<script setup lang="ts">
import { ref, onMounted, computed } from 'vue';
import LibroService from './services/LibroService';
import UtenteService from './services/UtenteService';
import AutoreService from './services/AutoreService';

// STATI REATTIVI
const listaLibri = ref<any[]>([]);
const listaUtenti = ref<any[]>([]);
const listaAutori = ref<any[]>([]);
const caricamentoLibri = ref(true);
const caricamentoUtenti = ref(true);
const caricamentoAutori = ref(true);
const erroreMessaggio = ref('');
const erroreNuovoUtente = ref('');
const erroreNuovoLibro = ref('');
const erroreNuovoAutore = ref('');
const testoRicerca = ref('');

// STATI DEI FORM
const nuovoUtente = ref({ nome: '', cognome: '', email: '', telefono: '', sesso: '', dataNascita: '' });

// STATO FORM LIBRO
const nuovoLibro = ref({
  codiceIsbn: '',
  titolo: '',
  annoPubblicazione: null,
  autore: { id: '' }
});

// STATO FORM AUTORE (Solo con date, senza nazionalità)
const nuovoAutore = ref({
  nome: '',
  cognome: '',
  dataNascita: '',
  dataMorte: ''
});

// CARICAMENTO DATI
const caricaDati = async () => {
  erroreMessaggio.value = '';
  try {
    listaLibri.value = await LibroService.getAllLibri();
  } catch (err) { erroreMessaggio.value = "Impossibile caricare i libri."; }
  finally { caricamentoLibri.value = false; }

  try {
    listaUtenti.value = await UtenteService.getAllUtenti();
  } catch (err) { console.error(err); }
  finally { caricamentoUtenti.value = false; }

  try {
    listaAutori.value = await AutoreService.getAllAutori();
  } catch (err) { console.error(err); }
  finally { caricamentoAutori.value = false; }
};

// METODI AUTORI
const aggiungiAutore = async () => {
  erroreNuovoAutore.value = '';
  if (!nuovoAutore.value.nome.trim() || !nuovoAutore.value.cognome.trim()) {
    erroreNuovoAutore.value = "Errore: Nome e Cognome dell'autore sono obbligatori!";
    return;
  }
  try {
    await AutoreService.createAutore(nuovoAutore.value);
    nuovoAutore.value = { nome: '', cognome: '', dataNascita: '', dataMorte: '' };
    alert("Autore registrato con successo nel database!");
    listaAutori.value = await AutoreService.getAllAutori();
  } catch (err: any) {
    if (err.response && err.response.data && err.response.data.message) {
      erroreNuovoAutore.value = err.response.data.message;
    } else {
      erroreNuovoAutore.value = "Errore durante il salvataggio dell'autore.";
    }
  }
};

const eliminaAutore = async (id: number) => {
  if (confirm("Sei sicuro di voler eliminare questo autore?")) {
    try {
      await AutoreService.deleteAutore(id);
      listaAutori.value = await AutoreService.getAllAutori();
      alert("Autore rimosso con successo!");
    } catch (err: any) {
      alert("Impossibile eliminare l'autore. Controlla che non ci siano libri associati a lui nel catalogo!");
    }
  }
};

// METODI LIBRI
const aggiungiLibro = async () => {
  erroreNuovoLibro.value = '';
  if (!nuovoLibro.value.codiceIsbn.trim() || !nuovoLibro.value.titolo.trim() || !nuovoLibro.value.autore.id) {
    erroreNuovoLibro.value = "Errore: ISBN, Titolo e Autore sono campi obbligatori!";
    return;
  }
  try {
    await LibroService.createLibro(nuovoLibro.value);
    nuovoLibro.value = { codiceIsbn: '', titolo: '', annoPubblicazione: null, autore: { id: '' } };
    alert("Libro salvato con successo!");
    listaLibri.value = await LibroService.getAllLibri();
  } catch (err: any) {
    if (err.response && err.response.data && err.response.data.message) {
      erroreNuovoLibro.value = err.response.data.message;
    } else {
      erroreNuovoLibro.value = "Errore durante il salvataggio. Controlla che l'ISBN non sia già esistente.";
    }
  }
};

const eliminaLibro = async (isbn: string) => {
  if (confirm(`Sei sicuro di voler eliminare il libro con ISBN ${isbn}?`)) {
    try {
      await LibroService.deleteLibro(isbn as any);
      listaLibri.value = await LibroService.getAllLibri();
      alert("Libro rimosso!");
    } catch (err: any) { alert("Impossibile eliminare il libro."); }
  }
};

// METODI UTENTI
const aggiungiUtente = async () => {
  erroreNuovoUtente.value = '';
  try {
    await UtenteService.createUtente(nuovoUtente.value);
    nuovoUtente.value = { nome: '', cognome: '', email: '', telefono: '', sesso: '', dataNascita: '' };
    alert("Utente registrato!");
    listaUtenti.value = await UtenteService.getAllUtenti();
  } catch (err: any) {
    if (err.response && err.response.data && err.response.data.message) {
      erroreNuovoUtente.value = err.response.data.message;
    } else { erroreNuovoUtente.value = "Errore nel salvataggio."; }
  }
};

const eliminaUtente = async (id: number) => {
  if (confirm("Sei sicuro di voler eliminare questo utente?")) {
    try {
      await UtenteService.deleteUtente(id);
      listaUtenti.value = await UtenteService.getAllUtenti();
      alert("Utente eliminato!");
    } catch (err: any) { alert("Impossibile eliminare."); }
  }
};

const libriFiltrati = computed(() => {
  if (!testoRicerca.value.trim()) return listaLibri.value;
  const query = testoRicerca.value.toLowerCase();
  return listaLibri.value.filter(libro => {
    return libro.titolo?.toLowerCase().includes(query) ||
        libro.autore?.nome?.toLowerCase().includes(query) ||
        libro.autore?.cognome?.toLowerCase().includes(query);
  });
});

onMounted(() => { caricaDati(); });
</script>

<template>
  <div style="padding: 40px; font-family: 'Segoe UI', Roboto, sans-serif; max-width: 1100px; margin: 0 auto; background-color: #f8f9fa; min-height: 100vh; display: flex; flex-direction: column; gap: 30px;">

    <div style="background: white; padding: 20px; border-radius: 8px; box-shadow: 0 4px 6px rgba(0,0,0,0.05); display: flex; align-items: center; gap: 15px;">
      <span style="font-size: 2.5rem;">🏛️</span>
      <div>
        <h1 style="margin: 0; color: #2c3e50; font-size: 1.8rem;">Pannello di Controllo Biblioteca</h1>
        <p style="margin: 5px 0 0 0; color: #7f8c8d; font-size: 0.9rem;">Gestione integrata del database MySQL</p>
      </div>
    </div>

    <div style="background: white; border-radius: 8px; box-shadow: 0 4px 6px rgba(0,0,0,0.05); padding: 20px;">
      <div style="display: flex; justify-content: space-between; align-items: center; border-bottom: 2px solid #0288d1; padding-bottom: 8px; margin-bottom: 20px;">
        <h3 style="margin: 0; color: #2c3e50;">📚 Gestione Catalogo Libri</h3>
        <span style="background: #e1f5fe; color: #0288d1; padding: 3px 10px; border-radius: 12px; font-size: 0.8rem; font-weight: bold;">Totale: {{ listaLibri.length }}</span>
      </div>

      <div style="background: #f0f9ff; padding: 20px; border: 1px solid #bae6fd; border-radius: 6px; margin-bottom: 25px;">
        <h4 style="margin-top: 0; margin-bottom: 15px; color: #0369a1;">➕ Aggiungi un Nuovo Libro al Catalogo</h4>
        <div v-if="erroreNuovoLibro" style="background: #ffe4e6; color: #b91c1c; padding: 10px; border-radius: 4px; margin-bottom: 15px;">⚠️ {{ erroreNuovoLibro }}</div>

        <form @submit.prevent="aggiungiLibro" style="display: grid; grid-template-columns: repeat(auto-fit, minmax(150px, 1fr)); gap: 15px; align-items: end;">
          <div><label style="display: block; font-size: 0.85rem; color: #0369a1; font-weight: 600; margin-bottom: 5px;">Codice ISBN *</label><input v-model="nuovoLibro.codiceIsbn" type="text" placeholder="Es. 978-8804" style="width: 100%; padding: 8px; border: 1px solid #7dd3fc; border-radius: 4px; outline: none;" /></div>
          <div><label style="display: block; font-size: 0.85rem; color: #0369a1; font-weight: 600; margin-bottom: 5px;">Titolo Libro *</label><input v-model="nuovoLibro.titolo" type="text" placeholder="Es. Ciao" style="width: 100%; padding: 8px; border: 1px solid #7dd3fc; border-radius: 4px; outline: none;" /></div>
          <div><label style="display: block; font-size: 0.85rem; color: #0369a1; font-weight: 600; margin-bottom: 5px;">Anno Pubblicazione</label><input v-model="nuovoLibro.annoPubblicazione" type="number" placeholder="Es. 1852" style="width: 100%; padding: 8px; border: 1px solid #7dd3fc; border-radius: 4px; outline: none;" /></div>
          <div>
            <label style="display: block; font-size: 0.85rem; color: #0369a1; font-weight: 600; margin-bottom: 5px;">Seleziona Autore *</label>
            <select v-model="nuovoLibro.autore.id" style="width: 100%; padding: 8px; border: 1px solid #7dd3fc; border-radius: 4px; outline: none; background: white;">
              <option value="" disabled selected>Scegli un autore...</option>
              <option v-for="autore in listaAutori" :key="autore.id" :value="autore.id">{{ autore.nome }} {{ autore.cognome }}</option>
            </select>
          </div>
          <button type="submit" style="background: #0288d1; color: white; border: none; padding: 10px; border-radius: 4px; cursor: pointer; font-weight: bold;">Salva Libro 💾</button>
        </form>
      </div>

      <div style="background: #f1f5f9; padding: 10px; border-radius: 6px; margin-bottom: 15px; display: flex; align-items: center; gap: 10px;">
        <span>🔍</span><input v-model="testoRicerca" type="text" placeholder="Filtra la tabella al volo..." style="width: 100%; padding: 8px; border: 1px solid #cbd5e1; border-radius: 4px; outline: none;"/>
      </div>

      <table v-if="libriFiltrati.length > 0" style="width: 100%; border-collapse: collapse; text-align: left;">
        <thead><tr style="background: #f8fafc; color: #64748b; font-size: 0.85rem;"><th style="padding: 10px;">ISBN</th><th style="padding: 10px;">Titolo</th><th style="padding: 10px;">Autore</th><th style="padding: 10px; text-align: center;">Azioni</th></tr></thead>
        <tbody>
        <tr v-for="libro in libriFiltrati" :key="libro.codiceIsbn" style="border-bottom: 1px solid #f1f5f9; font-size: 0.95rem;">
          <td style="padding: 10px; font-family: monospace;">{{ libro.codiceIsbn }}</td>
          <td style="padding: 10px; font-weight: 600;">{{ libro.titolo }}</td>
          <td style="padding: 10px;">{{ libro.autore?.nome }} {{ libro.autore?.cognome }}</td>
          <td style="padding: 10px; text-align: center;"><button @click="eliminaLibro(libro.codiceIsbn)" style="background: #ef4444; color: white; border: none; padding: 4px 10px; border-radius: 4px; cursor: pointer;">🗑️ Rimuovi</button></td>
        </tr>
        </tbody>
      </table>
    </div>

    <div style="background: white; border-radius: 8px; box-shadow: 0 4px 6px rgba(0,0,0,0.05); padding: 20px;">
      <div style="display: flex; justify-content: space-between; align-items: center; border-bottom: 2px solid #16a34a; padding-bottom: 8px; margin-bottom: 20px;">
        <h3 style="margin: 0; color: #2c3e50;">✍️ Area Gestione Autori</h3>
        <span style="background: #dcfce7; color: #16a34a; padding: 3px 10px; border-radius: 12px; font-size: 0.8rem; font-weight: bold;">Totale: {{ listaAutori.length }}</span>
      </div>

      <div style="background: #f0fdf4; padding: 20px; border: 1px solid #bbf7d0; border-radius: 6px; margin-bottom: 25px;">
        <h4 style="margin-top: 0; margin-bottom: 15px; color: #15803d;">➕ Registra un Nuovo Autore</h4>
        <div v-if="erroreNuovoAutore" style="background: #ffe4e6; color: #b91c1c; padding: 10px; border-radius: 4px; margin-bottom: 15px;">⚠️ {{ erroreNuovoAutore }}</div>

        <form @submit.prevent="aggiungiAutore" style="display: grid; grid-template-columns: repeat(auto-fit, minmax(180px, 1fr)); gap: 15px; align-items: end;">
          <div><label style="display: block; font-size: 0.85rem; color: #15803d; font-weight: 600; margin-bottom: 5px;">Nome Autore *</label><input v-model="nuovoAutore.nome" type="text" placeholder="Es. Italo" style="width: 100%; padding: 8px; border: 1px solid #86efac; border-radius: 4px; outline: none;" /></div>
          <div><label style="display: block; font-size: 0.85rem; color: #15803d; font-weight: 600; margin-bottom: 5px;">Cognome Autore *</label><input v-model="nuovoAutore.cognome" type="text" placeholder="Es. Calvino" style="width: 100%; padding: 8px; border: 1px solid #86efac; border-radius: 4px; outline: none;" /></div>
          <div><label style="display: block; font-size: 0.85rem; color: #15803d; font-weight: 600; margin-bottom: 5px;">Data di Nascita</label><input v-model="nuovoAutore.dataNascita" type="date" style="width: 100%; padding: 6px; border: 1px solid #86efac; border-radius: 4px; outline: none;" /></div>
          <div><label style="display: block; font-size: 0.85rem; color: #15803d; font-weight: 600; margin-bottom: 5px;">Data di Morte</label><input v-model="nuovoAutore.dataMorte" type="date" style="width: 100%; padding: 6px; border: 1px solid #86efac; border-radius: 4px; outline: none;" /></div>
          <button type="submit" style="background: #16a34a; color: white; border: none; padding: 10px; border-radius: 4px; cursor: pointer; font-weight: bold;">Salva Autore 💾</button>
        </form>
      </div>

      <p v-if="caricamentoAutori">Recupero autori da MySQL...</p>
      <div v-else-if="listaAutori.length > 0" style="overflow-x: auto;">
        <table style="width: 100%; border-collapse: collapse; text-align: left;">
          <thead>
          <tr style="background: #f8fafc; color: #64748b; font-size: 0.85rem; text-transform: uppercase;">
            <th style="padding: 10px;">ID</th><th style="padding: 10px;">Nome Completo</th><th style="padding: 10px;">Data Nascita</th><th style="padding: 10px;">Data Morte</th><th style="padding: 10px; text-align: center;">Azioni</th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="autore in listaAutori" :key="autore.id" style="border-bottom: 1px solid #f1f5f9; font-size: 0.95rem;">
            <td style="padding: 10px; color: #64748b;">#{{ autore.id }}</td>
            <td style="padding: 10px; font-weight: 500;">{{ autore.nome }} {{ autore.cognome }}</td>
            <td style="padding: 10px;">{{ autore.dataNascita || 'N/D' }}</td>
            <td style="padding: 10px;">{{ autore.dataMorte || 'N/D' }}</td>
            <td style="padding: 10px; text-align: center;"><button @click="eliminaAutore(autore.id)" style="background: #ef4444; color: white; border: none; padding: 4px 10px; border-radius: 4px; cursor: pointer;">🗑️ Elimina</button></td>
          </tr>
          </tbody>
        </table>
      </div>
    </div>

    <div style="background: white; border-radius: 8px; box-shadow: 0 4px 6px rgba(0,0,0,0.05); padding: 20px;">
      <div style="display: flex; justify-content: space-between; align-items: center; border-bottom: 2px solid #e11d48; padding-bottom: 8px; margin-bottom: 20px;">
        <h3 style="margin: 0; color: #2c3e50;">👥 Area Gestione Utenti</h3>
        <span style="background: #ffe4e6; color: #e11d48; padding: 3px 10px; border-radius: 12px; font-size: 0.8rem; font-weight: bold;">Totale: {{ listaUtenti.length }}</span>
      </div>
      <div style="background: #fff5f5; padding: 20px; border: 1px solid #fecdd3; border-radius: 6px; margin-bottom: 25px;">
        <h4 style="margin-top: 0; margin-bottom: 15px; color: #9f1239;">➕ Registra un Nuovo Utente</h4>
        <div v-if="erroreNuovoUtente" style="background: #ffe4e6; color: #b91c1c; padding: 10px; border-radius: 4px; margin-bottom: 15px;">⚠️ {{ erroreNuovoUtente }}</div>
        <form @submit.prevent="aggiungiUtente" style="display: grid; grid-template-columns: repeat(auto-fit, minmax(180px, 1fr)); gap: 15px; align-items: end;">
          <div><label style="display: block; font-size: 0.85rem; color: #4c0519; font-weight: 600; margin-bottom: 5px;">Nome *</label><input v-model="nuovoUtente.nome" type="text" style="width: 100%; padding: 8px; border: 1px solid #fda4af; border-radius: 4px; outline: none;" /></div>
          <div><label style="display: block; font-size: 0.85rem; color: #4c0519; font-weight: 600; margin-bottom: 5px;">Cognome *</label><input v-model="nuovoUtente.cognome" type="text" style="width: 100%; padding: 8px; border: 1px solid #fda4af; border-radius: 4px; outline: none;" /></div>
          <div><label style="display: block; font-size: 0.85rem; color: #4c0519; font-weight: 600; margin-bottom: 5px;">Sesso *</label><select v-model="nuovoUtente.sesso" style="width: 100%; padding: 8px; border: 1px solid #fda4af; border-radius: 4px; outline: none; background: white;"><option value="" disabled selected>Seleziona...</option><option value="M">Maschio (M)</option><option value="F">Femmina (F)</option><option value="Altro">Altro</option></select></div>
          <div><label style="display: block; font-size: 0.85rem; color: #4c0519; font-weight: 600; margin-bottom: 5px;">Data Nascita *</label><input v-model="nuovoUtente.dataNascita" type="date" style="width: 100%; padding: 6px; border: 1px solid #fda4af; border-radius: 4px; outline: none;" /></div>
          <div><label style="display: block; font-size: 0.85rem; color: #4c0519; font-weight: 600; margin-bottom: 5px;">Email *</label><input v-model="nuovoUtente.email" type="email" style="width: 100%; padding: 8px; border: 1px solid #fda4af; border-radius: 4px; outline: none;" /></div>
          <div><label style="display: block; font-size: 0.85rem; color: #4c0519; font-weight: 600; margin-bottom: 5px;">Telefono</label><input v-model="nuovoUtente.telefono" type="text" style="width: 100%; padding: 8px; border: 1px solid #fda4af; border-radius: 4px; outline: none;" /></div>
          <button type="submit" style="background: #e11d48; color: white; border: none; padding: 10px; border-radius: 4px; cursor: pointer; font-weight: bold;">Salva nel DB 💾</button>
        </form>
      </div>
      <table v-if="listaUtenti.length > 0" style="width: 100%; border-collapse: collapse; text-align: left;">
        <thead><tr style="background: #f8fafc; color: #64748b; font-size: 0.85rem;"><th style="padding: 10px;">ID</th><th style="padding: 10px;">Nome Completo</th><th style="padding: 10px; text-align: center;">Sesso</th><th style="padding: 10px;">Data Nascita</th><th style="padding: 10px;">Email</th><th style="padding: 10px;">Telefono</th><th style="padding: 10px; text-align: center;">Azioni</th></tr></thead>
        <tbody>
        <tr v-for="utente in listaUtenti" :key="utente.id" style="border-bottom: 1px solid #f1f5f9; font-size: 0.95rem;">
          <td style="padding: 10px; color: #64748b;">#{{ utente.id }}</td><td style="padding: 10px; font-weight: 500;">{{ utente.nome }} {{ utente.cognome }}</td><td style="padding: 10px; text-align: center;"><span style="background: #f1f5f9; padding: 2px 6px; border-radius: 4px; font-weight: bold;">{{ utente.sesso }}</span></td><td style="padding: 10px;">{{ utente.dataNascita }}</td><td style="padding: 10px;">{{ utente.email }}</td><td style="padding: 10px; color: #64748b;">{{ utente.telefono || 'N/D' }}</td>
          <td style="padding: 10px; text-align: center;"><button @click="eliminaUtente(utente.id)" style="background: #ef4444; color: white; border: none; padding: 4px 10px; border-radius: 4px; cursor: pointer;">🗑️ Elimina</button></td>
        </tr>
        </tbody>
      </table>
    </div>

  </div>
</template>