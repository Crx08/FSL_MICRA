<script setup lang="ts">
import { ref, onMounted } from 'vue';
import axios from 'axios';


interface Autore {
  id?: number;
  nome: string;
  cognome: string;
  dataNascita?: string;
  dataMorte?: string;
}

interface Libro {
  codiceIsbn: string;
  titolo: string;
  annoPubblicazione?: number | null;
  autore: { id: number | string };
  sala?: string;
  scaffale?: string;
  ripiano?: string;
}

interface Utente {
  id?: number;
  nome: string;
  cognome: string;
  sesso?: string;
  dataNascita?: string;
  luogoNascita?: string;
  email: string;
  telefono?: string;
}

interface CopiaLibro {
  idCopia: number;
  disponibile: boolean;
  statoConservazione: string;
  libro: Libro;
}

interface Prestito {
  idPrestito: number;
  utente: Utente;
  copiaLibro: CopiaLibro;
  dataInizio: string;
  dataScadenza: string;
  dataRestituzione?: string | null;
}


const sezioneAttiva = ref<'utenti' | 'libri' | 'autori' | 'prestiti'>('utenti');

const listaUtenti   = ref<Utente[]>([]);
const listaLibri    = ref<Libro[]>([]);
const listaAutori   = ref<Autore[]>([]);
const listaPrestiti = ref<Prestito[]>([]);
const listaCopie    = ref<CopiaLibro[]>([]);

// Copie per libro (sezione libri)
const copieLibroSelezionato = ref<CopiaLibro[]>([]);
const isbnLibroSelezionato  = ref('');
const titoloLibroSelezionato = ref('');

const messaggioSuccesso = ref('');
const messaggioErrore   = ref('');

function mostraSuccesso(msg: string) {
  messaggioSuccesso.value = msg;
  messaggioErrore.value   = '';
  setTimeout(() => { messaggioSuccesso.value = ''; }, 4000);
}
function mostraErrore(msg: string) {
  messaggioErrore.value   = msg;
  messaggioSuccesso.value = '';
  setTimeout(() => { messaggioErrore.value = ''; }, 6000);
}

// Utenti

const nuovoUtente = ref<Utente>({
  nome: '', cognome: '', sesso: '', dataNascita: '', luogoNascita: '', email: '', telefono: ''
});

async function caricaUtenti() {
  try {
    const res = await axios.get('http://localhost:8080/utenti');
    listaUtenti.value = res.data;
  } catch (e) { console.error(e); }
}

async function aggiungiUtente() {
  if (!nuovoUtente.value.nome.trim() || !nuovoUtente.value.cognome.trim() || !nuovoUtente.value.email.trim()) {
    mostraErrore('Nome, cognome ed email sono obbligatori.');
    return;
  }
  try {
    await axios.post('http://localhost:8080/utenti', nuovoUtente.value);
    nuovoUtente.value = { nome: '', cognome: '', sesso: '', dataNascita: '', luogoNascita: '', email: '', telefono: '' };
    mostraSuccesso('Utente aggiunto con successo!');
    caricaUtenti();
  } catch (e: any) {
    const msg = e.response?.data?.message || e.response?.data || 'Errore durante il salvataggio.';
    mostraErrore(msg);
  }
}

async function eliminaUtente(id: number) {
  if (!confirm('Sei sicuro di voler eliminare questo utente?')) return;
  try {
    await axios.delete('http://localhost:8080/utenti/' + id);
    mostraSuccesso('Utente eliminato con successo.');
    caricaUtenti();
  } catch (e: any) {
    const msg = e.response?.data?.message || e.response?.data || 'Errore durante eliminazione.';
    mostraErrore(msg);
  }
}

// Libri

const nuovoLibro = ref({
  codiceIsbn: '', titolo: '', annoPubblicazione: null as number | null,
  autore: { id: '' as string | number }
});

async function caricaLibri() {
  try {
    const res = await axios.get('http://localhost:8080/libri');
    listaLibri.value = res.data;
  } catch (e) { console.error(e); }
}

async function aggiungiLibro() {
  if (!nuovoLibro.value.codiceIsbn.trim() || !nuovoLibro.value.titolo.trim() || !nuovoLibro.value.autore.id) {
    mostraErrore('ISBN, titolo e autore sono obbligatori.');
    return;
  }
  try {
    await axios.post('http://localhost:8080/libri', nuovoLibro.value);
    nuovoLibro.value = { codiceIsbn: '', titolo: '', annoPubblicazione: null, autore: { id: '' } };
    mostraSuccesso('Libro aggiunto al catalogo!');
    caricaLibri();
  } catch (e: any) {
    const msg = e.response?.data || 'Errore durante il salvataggio.';
    mostraErrore(msg);
  }
}

async function eliminaLibro(isbn: string) {
  if (!confirm('Sei sicuro di voler eliminare questo libro?')) return;
  try {
    await axios.delete('http://localhost:8080/libri/' + isbn);
    mostraSuccesso('Libro eliminato dal catalogo.');
    caricaLibri();
  } catch (e: any) {
    const msg = e.response?.data || 'Errore durante eliminazione.';
    mostraErrore(msg);
  }
}

async function aggiungiCopie(isbn: string, titolo: string) {
  const quantitaStr = prompt('Quante copie vuoi aggiungere per "' + titolo + '"?', '2');
  if (!quantitaStr) return;
  const quantita = parseInt(quantitaStr);
  if (isNaN(quantita) || quantita < 1 || quantita > 50) {
    mostraErrore('Inserisci un numero valido tra 1 e 50.');
    return;
  }
  const stato = prompt('Stato di conservazione delle copie?\n1 = Nuovo\n2 = Ottimo\n3 = Buono\n4 = Usato\n5 = Rovinato\n\nScrivi il numero:', '3');
  if (!stato) return;
  const statoMap: Record<string, string> = { '1': 'Nuovo', '2': 'Ottimo', '3': 'Buono', '4': 'Usato', '5': 'Rovinato' };
  const statoFinale = statoMap[stato.trim()] || 'Buono';
  try {
    const res = await axios.post('http://localhost:8080/libri/' + isbn + '/copie', null, {
      params: { quantita, stato: statoFinale }
    });
    mostraSuccesso(res.data);
  } catch (e: any) {
    mostraErrore(e.response?.data || 'Errore durante aggiunta copie.');
  }
}

async function vediCopieLibro(isbn: string, titolo: string) {
  if (isbnLibroSelezionato.value === isbn) {
    // se clicco di nuovo sullo stesso libro, chiudo il pannello
    isbnLibroSelezionato.value = '';
    copieLibroSelezionato.value = [];
    titoloLibroSelezionato.value = '';
    return;
  }
  try {
    const res = await axios.get('http://localhost:8080/libri/' + isbn + '/copie');
    copieLibroSelezionato.value = res.data;
    isbnLibroSelezionato.value  = isbn;
    titoloLibroSelezionato.value = titolo;
  } catch (e: any) {
    mostraErrore(e.response?.data || 'Errore nel caricamento copie.');
  }
}

const nuovoAutore = ref<Autore>({ nome: '', cognome: '', dataNascita: '', dataMorte: '' });

async function caricaAutori() {
  try {
    const res = await axios.get('http://localhost:8080/autori');
    listaAutori.value = res.data;
  } catch (e) { console.error(e); }
}

async function aggiungiAutore() {
  if (!nuovoAutore.value.nome.trim() || !nuovoAutore.value.cognome.trim()) {
    mostraErrore('Nome e cognome autore sono obbligatori.');
    return;
  }
  try {
    const payload: any = {
      nome: nuovoAutore.value.nome,
      cognome: nuovoAutore.value.cognome,
    };
    if (nuovoAutore.value.dataNascita) payload.dataNascita = nuovoAutore.value.dataNascita;
    if (nuovoAutore.value.dataMorte)   payload.dataMorte   = nuovoAutore.value.dataMorte;
    await axios.post('http://localhost:8080/autori', payload);
    nuovoAutore.value = { nome: '', cognome: '', dataNascita: '', dataMorte: '' };
    mostraSuccesso('Autore aggiunto con successo!');
    caricaAutori();
  } catch (e: any) {
    const msg = e.response?.data || 'Errore durante il salvataggio.';
    mostraErrore(msg);
  }
}

async function eliminaAutore(id: number) {
  if (!confirm('Sei sicuro di voler eliminare questo autore?')) return;
  try {
    await axios.delete('http://localhost:8080/autori/' + id);
    mostraSuccesso('Autore eliminato con successo.');
    caricaAutori();
  } catch (e: any) {
    const msg = e.response?.data || 'Errore durante eliminazione.';
    mostraErrore(msg);
  }
}

// Prestiti

const nuovoPrestito = ref({
  idUtente: '' as string | number,
  nomeUtente: '',
  cognomeUtente: '',
  idCopia: '' as string | number,
});

const isbnPerCopie = ref('');

async function caricaPrestiti() {
  try {
    const res = await axios.get('http://localhost:8080/prestiti');
    listaPrestiti.value = res.data;
  } catch (e) { console.error(e); }
}

async function caricaCopieDisponibili() {
  if (!isbnPerCopie.value.trim()) {
    mostraErrore('Inserisci un ISBN per cercare le copie.');
    return;
  }
  try {
    const res = await axios.get('http://localhost:8080/libri/' + isbnPerCopie.value.trim() + '/copie');
    listaCopie.value = (res.data as CopiaLibro[]).filter(c => c.disponibile);
    if (listaCopie.value.length === 0) {
      mostraErrore('Nessuna copia disponibile per questo ISBN.');
    }
  } catch (e: any) {
    const msg = e.response?.data || 'Errore nel caricamento copie.';
    mostraErrore(msg);
    listaCopie.value = [];
  }
}

async function attivaPrestito() {
  if (!nuovoPrestito.value.idUtente || !nuovoPrestito.value.nomeUtente.trim() ||
      !nuovoPrestito.value.cognomeUtente.trim() || !nuovoPrestito.value.idCopia) {
    mostraErrore('Tutti i campi del prestito sono obbligatori.');
    return;
  }
  try {
    const res = await axios.post('http://localhost:8080/prestiti/attiva', null, {
      params: {
        idUtente:      nuovoPrestito.value.idUtente,
        nomeUtente:    nuovoPrestito.value.nomeUtente,
        cognomeUtente: nuovoPrestito.value.cognomeUtente,
        idCopia:       nuovoPrestito.value.idCopia,
      }
    });
    nuovoPrestito.value = { idUtente: '', nomeUtente: '', cognomeUtente: '', idCopia: '' };
    listaCopie.value = [];
    isbnPerCopie.value = '';
    mostraSuccesso(res.data);
    caricaPrestiti();
  } catch (e: any) {
    mostraErrore(e.response?.data || 'Errore durante attivazione prestito.');
  }
}

async function restituisciLibro(idPrestito: number) {
  if (!confirm('Confermi la restituzione di questo libro?')) return;
  try {
    const res = await axios.post('http://localhost:8080/prestiti/restituisci', null, {
      params: { idPrestito }
    });
    mostraSuccesso(res.data);
    caricaPrestiti();
  } catch (e: any) {
    mostraErrore(e.response?.data || 'Errore durante la restituzione.');
  }
}

async function eliminaPrestito(idPrestito: number) {
  if (!confirm('Sei sicuro di voler eliminare questo prestito dallo storico?')) return;
  try {
    await axios.delete('http://localhost:8080/prestiti/' + idPrestito);
    mostraSuccesso('Prestito eliminato dallo storico.');
    caricaPrestiti();
  } catch (e: any) {
    mostraErrore(e.response?.data || 'Errore durante eliminazione prestito.');
  }
}

function isScaduto(dataScadenza: string, dataRestituzione?: string | null): boolean {
  if (dataRestituzione) return false;
  return new Date(dataScadenza) < new Date();
}


onMounted(async () => {
  await caricaAutori();
  await caricaUtenti();
  await caricaLibri();
  await caricaPrestiti();
});
</script>

<template>
  <div class="page">
    <header class="header">
      <h1>🏛️ Biblioteca — Pannello di Gestione</h1>
    </header>

    <div v-if="messaggioSuccesso" class="notifica successo">✅ {{ messaggioSuccesso }}</div>
    <div v-if="messaggioErrore"   class="notifica errore">⚠️ {{ messaggioErrore }}</div>

    <nav class="tab-nav">
      <button :class="['tab', sezioneAttiva === 'utenti'   ? 'attivo' : '']" @click="sezioneAttiva = 'utenti'">👤 Utenti</button>
      <button :class="['tab', sezioneAttiva === 'libri'    ? 'attivo' : '']" @click="sezioneAttiva = 'libri'">📚 Libri</button>
      <button :class="['tab', sezioneAttiva === 'autori'   ? 'attivo' : '']" @click="sezioneAttiva = 'autori'">✍️ Autori</button>
      <button :class="['tab', sezioneAttiva === 'prestiti' ? 'attivo' : '']" @click="sezioneAttiva = 'prestiti'">📋 Prestiti</button>
    </nav>

    <!-- Utenti -->
    <section v-if="sezioneAttiva === 'utenti'" class="sezione">
      <div class="card">
        <h2>➕ Inserisci Nuovo Utente</h2>
        <div class="form-grid">
          <div class="campo">
            <label>Nome *</label>
            <input v-model="nuovoUtente.nome" placeholder="es. Mario" />
          </div>
          <div class="campo">
            <label>Cognome *</label>
            <input v-model="nuovoUtente.cognome" placeholder="es. Rossi" />
          </div>
          <div class="campo">
            <label>Sesso</label>
            <select v-model="nuovoUtente.sesso">
              <option value="">-- Seleziona --</option>
              <option value="M">Maschio</option>
              <option value="F">Femmina</option>
            </select>
          </div>
          <div class="campo">
            <label>Data di Nascita</label>
            <input v-model="nuovoUtente.dataNascita" type="date" />
          </div>
          <div class="campo">
            <label>Luogo di Nascita</label>
            <input v-model="nuovoUtente.luogoNascita" placeholder="es. Roma" />
          </div>
          <div class="campo">
            <label>Email *</label>
            <input v-model="nuovoUtente.email" type="email" placeholder="mario@email.it" />
          </div>
          <div class="campo">
            <label>Telefono</label>
            <input v-model="nuovoUtente.telefono" placeholder="es. 3331234567" />
          </div>
        </div>
        <button class="btn-primario" @click="aggiungiUtente">💾 Salva Utente</button>
      </div>

      <div class="card">
        <h2>📋 Elenco Utenti ({{ listaUtenti.length }})</h2>
        <table class="tabella">
          <thead>
          <tr>
            <th>ID</th><th>Nome</th><th>Cognome</th><th>Sesso</th>
            <th>Data Nascita</th><th>Email</th><th>Telefono</th><th>Azioni</th>
          </tr>
          </thead>
          <tbody>
          <tr v-if="listaUtenti.length === 0">
            <td colspan="8" class="vuoto">Nessun utente registrato.</td>
          </tr>
          <tr v-for="u in listaUtenti" :key="u.id">
            <td>{{ u.id }}</td>
            <td>{{ u.nome }}</td>
            <td>{{ u.cognome }}</td>
            <td>{{ u.sesso || '—' }}</td>
            <td>{{ u.dataNascita || '—' }}</td>
            <td>{{ u.email }}</td>
            <td>{{ u.telefono || '—' }}</td>
            <td>
              <button class="btn-elimina" @click="eliminaUtente(u.id!)">🗑️ Elimina</button>
            </td>
          </tr>
          </tbody>
        </table>
      </div>
    </section>

    <!-- Libri -->
    <section v-if="sezioneAttiva === 'libri'" class="sezione">
      <div class="card">
        <h2>➕ Inserisci Nuovo Libro</h2>
        <div class="form-grid">
          <div class="campo">
            <label>Codice ISBN *</label>
            <input v-model="nuovoLibro.codiceIsbn" placeholder="es. 978-88-04-67543-1" />
          </div>
          <div class="campo">
            <label>Titolo *</label>
            <input v-model="nuovoLibro.titolo" placeholder="es. Il Nome della Rosa" />
          </div>
          <div class="campo">
            <label>Anno Pubblicazione</label>
            <input v-model.number="nuovoLibro.annoPubblicazione" type="number" placeholder="es. 1980" min="1000" max="2100" />
          </div>
          <div class="campo">
            <label>Autore *</label>
            <select v-model="nuovoLibro.autore.id">
              <option value="" disabled>-- Seleziona Autore --</option>
              <option v-for="a in listaAutori" :key="a.id" :value="a.id">
                {{ a.cognome }} {{ a.nome }}
              </option>
            </select>
          </div>
        </div>
        <button class="btn-primario" @click="aggiungiLibro">💾 Salva Libro</button>
      </div>

      <div class="card">
        <h2>📚 Catalogo Libri ({{ listaLibri.length }})</h2>
        <table class="tabella">
          <thead>
          <tr>
            <th>ISBN</th><th>Titolo</th><th>Autore</th><th>Anno</th><th>Azioni</th>
          </tr>
          </thead>
          <tbody>
          <tr v-if="listaLibri.length === 0">
            <td colspan="5" class="vuoto">Nessun libro nel catalogo.</td>
          </tr>
          <template v-for="l in listaLibri" :key="l.codiceIsbn">
            <tr :class="isbnLibroSelezionato === l.codiceIsbn ? 'riga-selezionata' : ''">
              <td>{{ l.codiceIsbn }}</td>
              <td>{{ l.titolo }}</td>
              <td>{{ (l.autore as any)?.cognome }} {{ (l.autore as any)?.nome }}</td>
              <td>{{ l.annoPubblicazione || '—' }}</td>
              <td class="azioni-cell">
                <button class="btn-vedi-copie" @click="vediCopieLibro(l.codiceIsbn, l.titolo)">
                  {{ isbnLibroSelezionato === l.codiceIsbn ? '🔼 Chiudi' : '📋 Copie' }}
                </button>
                <button class="btn-copie" @click="aggiungiCopie(l.codiceIsbn, l.titolo)">📦 Aggiungi</button>
                <button class="btn-elimina" @click="eliminaLibro(l.codiceIsbn)">🗑️</button>
              </td>
            </tr>
            <!-- Pannello copie espandibile -->
            <tr v-if="isbnLibroSelezionato === l.codiceIsbn">
              <td colspan="5" style="padding: 0;">
                <div class="pannello-copie">
                  <h4>📋 Copie di "{{ titoloLibroSelezionato }}" ({{ copieLibroSelezionato.length }} totali)</h4>
                  <div v-if="copieLibroSelezionato.length === 0" class="vuoto">Nessuna copia registrata per questo libro.</div>
                  <table v-else class="tabella-copie">
                    <thead>
                    <tr>
                      <th>ID Copia</th>
                      <th>Stato Conservazione</th>
                      <th>Disponibilità</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr v-for="c in copieLibroSelezionato" :key="c.idCopia">
                      <td>#{{ c.idCopia }}</td>
                      <td>{{ c.statoConservazione }}</td>
                      <td>
                            <span :class="c.disponibile ? 'badge-disponibile' : 'badge-non-disponibile'">
                              {{ c.disponibile ? '✅ Disponibile' : '❌ In prestito' }}
                            </span>
                      </td>
                    </tr>
                    </tbody>
                  </table>
                </div>
              </td>
            </tr>
          </template>
          </tbody>
        </table>
      </div>
    </section>

    <!-- Autori -->
    <section v-if="sezioneAttiva === 'autori'" class="sezione">
      <div class="card">
        <h2>➕ Inserisci Nuovo Autore</h2>
        <div class="form-grid">
          <div class="campo">
            <label>Nome *</label>
            <input v-model="nuovoAutore.nome" placeholder="es. Umberto" />
          </div>
          <div class="campo">
            <label>Cognome *</label>
            <input v-model="nuovoAutore.cognome" placeholder="es. Eco" />
          </div>
          <div class="campo">
            <label>Data di Nascita</label>
            <input v-model="nuovoAutore.dataNascita" type="date" />
          </div>
          <div class="campo">
            <label>Data di Morte <span style="font-weight:400; color:#888">(se deceduto)</span></label>
            <input v-model="nuovoAutore.dataMorte" type="date" />
          </div>
        </div>
        <button class="btn-primario" @click="aggiungiAutore">💾 Salva Autore</button>
      </div>

      <div class="card">
        <h2>✍️ Elenco Autori ({{ listaAutori.length }})</h2>
        <table class="tabella">
          <thead>
          <tr>
            <th>ID</th><th>Cognome</th><th>Nome</th><th>Data Nascita</th><th>Data Morte</th><th>Azioni</th>
          </tr>
          </thead>
          <tbody>
          <tr v-if="listaAutori.length === 0">
            <td colspan="6" class="vuoto">Nessun autore registrato.</td>
          </tr>
          <tr v-for="a in listaAutori" :key="a.id">
            <td>{{ a.id }}</td>
            <td>{{ a.cognome }}</td>
            <td>{{ a.nome }}</td>
            <td>{{ a.dataNascita || '—' }}</td>
            <td>{{ a.dataMorte || '—' }}</td>
            <td>
              <button class="btn-elimina" @click="eliminaAutore(a.id!)">🗑️ Elimina</button>
            </td>
          </tr>
          </tbody>
        </table>
      </div>
    </section>

    <!-- Prestiti -->
    <section v-if="sezioneAttiva === 'prestiti'" class="sezione">

      <div class="card">
        <h2>➕ Nuovo Prestito</h2>
        <p class="nota-info">
          🔒 Per sicurezza il sistema verifica che l'ID, nome e cognome dell'utente coincidano
          con quelli registrati prima di autorizzare il prestito.
        </p>
        <div class="form-grid">
          <div class="campo">
            <label>ID Utente *</label>
            <input v-model.number="nuovoPrestito.idUtente" type="number" placeholder="es. 3" />
          </div>
          <div class="campo">
            <label>Nome Utente * <span style="color:#888;font-weight:400">(deve corrispondere)</span></label>
            <input v-model="nuovoPrestito.nomeUtente" placeholder="es. Mario" />
          </div>
          <div class="campo">
            <label>Cognome Utente * <span style="color:#888;font-weight:400">(deve corrispondere)</span></label>
            <input v-model="nuovoPrestito.cognomeUtente" placeholder="es. Rossi" />
          </div>
        </div>

        <div class="cerca-copie">
          <label>Cerca copie disponibili per ISBN:</label>
          <div class="cerca-row">
            <input v-model="isbnPerCopie" placeholder="es. 978-88-04-67543-1" />
            <button class="btn-secondario" @click="caricaCopieDisponibili">🔍 Cerca Copie</button>
          </div>
        </div>

        <div v-if="listaCopie.length > 0" class="campo">
          <label>Seleziona Copia Disponibile *</label>
          <select v-model="nuovoPrestito.idCopia">
            <option value="" disabled>-- Seleziona una copia --</option>
            <option v-for="c in listaCopie" :key="c.idCopia" :value="c.idCopia">
              Copia #{{ c.idCopia }} — {{ c.libro.titolo }} ({{ c.statoConservazione }})
            </option>
          </select>
        </div>

        <button class="btn-primario" @click="attivaPrestito">📤 Attiva Prestito</button>
      </div>

      <div class="card">
        <h2>📋 Storico Prestiti ({{ listaPrestiti.length }})</h2>

        <div class="legenda">
          <span class="badge attivo-badge">Attivo</span> — in corso &nbsp;|&nbsp;
          <span class="badge scaduto-badge">Scaduto</span> — non ancora restituito oltre la data &nbsp;|&nbsp;
          <span class="badge restituito-badge">Restituito</span> — chiuso
        </div>

        <table class="tabella">
          <thead>
          <tr>
            <th>ID</th>
            <th>Utente</th>
            <th>Libro (Copia)</th>
            <th>Data Inizio</th>
            <th>Scadenza</th>
            <th>Restituzione</th>
            <th>Stato</th>
            <th>Azioni</th>
          </tr>
          </thead>
          <tbody>
          <tr v-if="listaPrestiti.length === 0">
            <td colspan="8" class="vuoto">Nessun prestito registrato.</td>
          </tr>
          <tr v-for="p in listaPrestiti" :key="p.idPrestito"
              :class="p.dataRestituzione ? 'riga-restituita' : isScaduto(p.dataScadenza) ? 'riga-scaduta' : ''">
            <td>{{ p.idPrestito }}</td>
            <td>{{ p.utente?.cognome }} {{ p.utente?.nome }}</td>
            <td>{{ p.copiaLibro?.libro?.titolo }} <span class="copia-id">#{{ p.copiaLibro?.idCopia }}</span></td>
            <td>{{ p.dataInizio }}</td>
            <td>{{ p.dataScadenza }}</td>
            <td>{{ p.dataRestituzione || '—' }}</td>
            <td>
              <span v-if="p.dataRestituzione"           class="badge restituito-badge">Restituito</span>
              <span v-else-if="isScaduto(p.dataScadenza)" class="badge scaduto-badge">Scaduto</span>
              <span v-else                              class="badge attivo-badge">Attivo</span>
            </td>
            <td class="azioni-cell">
              <button
                  v-if="!p.dataRestituzione"
                  class="btn-restituzione"
                  @click="restituisciLibro(p.idPrestito)"
              >📥 Restituisci</button>
              <button class="btn-elimina" @click="eliminaPrestito(p.idPrestito)">🗑️</button>
            </td>
          </tr>
          </tbody>
        </table>
      </div>
    </section>
  </div>
</template> <!-- HTML-->

<style scoped>

.page {
  font-family: 'Segoe UI', sans-serif;
  background: #f0f4f8;
  min-height: 100vh;
  padding-bottom: 60px;
}

.header {
  background: #1a237e;
  color: white;
  padding: 20px 40px;
}
.header h1 { margin: 0; font-size: 1.6rem; font-weight: 600; }


.notifica {
  margin: 16px 40px 0;
  padding: 12px 20px;
  border-radius: 8px;
  font-weight: 600;
}
.successo { background: #e8f5e9; color: #2e7d32; border-left: 4px solid #43a047; }
.errore   { background: #ffebee; color: #b71c1c; border-left: 4px solid #e53935; }


.tab-nav {
  display: flex;
  gap: 0;
  background: white;
  border-bottom: 2px solid #c5cae9;
  padding: 0 40px;
  margin-top: 20px;
}
.tab {
  padding: 14px 28px;
  border: none;
  background: transparent;
  cursor: pointer;
  font-size: 1rem;
  font-weight: 600;
  color: #5c6bc0;
  border-bottom: 3px solid transparent;
  margin-bottom: -2px;
  transition: all 0.2s;
}
.tab:hover { background: #e8eaf6; }
.tab.attivo {
  color: #1a237e;
  border-bottom-color: #1a237e;
  background: #e8eaf6;
}


.sezione { padding: 30px 40px; display: flex; flex-direction: column; gap: 24px; }

.card {
  background: white;
  border-radius: 10px;
  padding: 28px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.08);
}
.card h2 { margin: 0 0 20px; font-size: 1.2rem; color: #1a237e; }


.form-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(260px, 1fr));
  gap: 16px;
  margin-bottom: 20px;
}
.campo { display: flex; flex-direction: column; gap: 6px; }
.campo label { font-size: 0.85rem; font-weight: 600; color: #37474f; }
.campo input, .campo select {
  padding: 10px 12px;
  border: 1px solid #cfd8dc;
  border-radius: 6px;
  font-size: 0.95rem;
  transition: border 0.2s;
}
.campo input:focus, .campo select:focus {
  outline: none;
  border-color: #3f51b5;
  box-shadow: 0 0 0 2px rgba(63,81,181,0.15);
}

.nota-info {
  background: #e3f2fd;
  border-left: 4px solid #1976d2;
  padding: 10px 14px;
  border-radius: 4px;
  color: #0d47a1;
  margin-bottom: 20px;
  font-size: 0.9rem;
}

.cerca-copie { margin-bottom: 16px; }
.cerca-copie label { display: block; font-size: 0.85rem; font-weight: 600; color: #37474f; margin-bottom: 8px; }
.cerca-row { display: flex; gap: 10px; }
.cerca-row input {
  flex: 1;
  padding: 10px 12px;
  border: 1px solid #cfd8dc;
  border-radius: 6px;
  font-size: 0.95rem;
}


.btn-primario {
  padding: 12px 28px;
  background: #1a237e;
  color: white;
  border: none;
  border-radius: 6px;
  font-size: 1rem;
  font-weight: 600;
  cursor: pointer;
  transition: background 0.2s;
}
.btn-primario:hover { background: #283593; }

.btn-secondario {
  padding: 10px 18px;
  background: #5c6bc0;
  color: white;
  border: none;
  border-radius: 6px;
  font-size: 0.9rem;
  font-weight: 600;
  cursor: pointer;
  white-space: nowrap;
}
.btn-secondario:hover { background: #3f51b5; }

.btn-elimina {
  padding: 6px 12px;
  background: #e53935;
  color: white;
  border: none;
  border-radius: 5px;
  font-size: 0.82rem;
  cursor: pointer;
  font-weight: 600;
}
.btn-elimina:hover { background: #b71c1c; }

.btn-copie {
  padding: 6px 10px;
  background: #f57c00;
  color: white;
  border: none;
  border-radius: 5px;
  font-size: 0.82rem;
  cursor: pointer;
  font-weight: 600;
  margin-right: 6px;
}
.btn-copie:hover { background: #e65100; }

.btn-vedi-copie {
  padding: 6px 10px;
  background: #5c6bc0;
  color: white;
  border: none;
  border-radius: 5px;
  font-size: 0.82rem;
  cursor: pointer;
  font-weight: 600;
  margin-right: 6px;
}
.btn-vedi-copie:hover { background: #3949ab; }

.riga-selezionata { background: #e8eaf6 !important; }

.pannello-copie {
  background: #f5f5f5;
  border-top: 2px solid #c5cae9;
  border-bottom: 2px solid #c5cae9;
  padding: 16px 24px;
}
.pannello-copie h4 { margin: 0 0 12px; color: #1a237e; font-size: 0.95rem; }

.tabella-copie { width: 100%; border-collapse: collapse; font-size: 0.88rem; }
.tabella-copie th { padding: 8px 12px; background: #e8eaf6; text-align: left; color: #1a237e; font-weight: 600; }
.tabella-copie td { padding: 8px 12px; border-bottom: 1px solid #e0e0e0; }

.badge-disponibile    { background: #e8f5e9; color: #2e7d32; padding: 3px 10px; border-radius: 20px; font-weight: 600; font-size: 0.82rem; }
.badge-non-disponibile { background: #ffebee; color: #b71c1c; padding: 3px 10px; border-radius: 20px; font-weight: 600; font-size: 0.82rem; }

.btn-restituzione {
  padding: 6px 10px;
  background: #2e7d32;
  color: white;
  border: none;
  border-radius: 5px;
  font-size: 0.82rem;
  cursor: pointer;
  font-weight: 600;
  margin-right: 6px;
}
.btn-restituzione:hover { background: #1b5e20; }


.tabella { width: 100%; border-collapse: collapse; font-size: 0.9rem; }
.tabella thead tr { background: #e8eaf6; }
.tabella th { padding: 12px 10px; text-align: left; font-weight: 600; color: #1a237e; border-bottom: 2px solid #c5cae9; }
.tabella td { padding: 10px 10px; border-bottom: 1px solid #eceff1; vertical-align: middle; }
.tabella tbody tr:hover { background: #f5f5f5; }

.riga-restituita td { color: #78909c; }
.riga-scaduta td   { color: #b71c1c; }

.vuoto { text-align: center; padding: 30px; color: #90a4ae; font-style: italic; }
.copia-id { font-size: 0.78rem; color: #90a4ae; margin-left: 4px; }
.azioni-cell { white-space: nowrap; }


.badge {
  display: inline-block;
  padding: 3px 10px;
  border-radius: 20px;
  font-size: 0.78rem;
  font-weight: 700;
}
.attivo-badge     { background: #e3f2fd; color: #0d47a1; }
.scaduto-badge    { background: #ffebee; color: #b71c1c; }
.restituito-badge { background: #e8f5e9; color: #1b5e20; }


.legenda { font-size: 0.85rem; color: #546e7a; margin-bottom: 14px; }
</style>
