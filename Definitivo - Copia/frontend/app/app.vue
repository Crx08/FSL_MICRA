<script setup lang="ts">
import { ref, onMounted, computed } from 'vue';
import LibroService from './services/LibroService';
import UtenteService from './services/UtenteService';
import AutoreService from './services/AutoreService';

const listaLibri = ref<any[]>([]);
const listaUtenti = ref<any[]>([]);
const listaAutori = ref<any[]>([]);
const erroreNuovoLibro = ref('');

const nuovoLibro = ref({
  codiceIsbn: '',
  titolo: '',
  annoPubblicazione: null,
  autore: { id: '' }
});

const caricaDati = async () => {
  try {
    listaLibri.value = await LibroService.getAllLibri();
    listaUtenti.value = await UtenteService.getAllUtenti();
    listaAutori.value = await AutoreService.getAllAutori();
  } catch (err) { console.error("Errore nel caricamento:", err); }
};

const aggiungiLibro = async () => {
  erroreNuovoLibro.value = '';
  if (!nuovoLibro.value.codiceIsbn || !nuovoLibro.value.titolo || !nuovoLibro.value.autore.id) {
    erroreNuovoLibro.value = "Attenzione: ISBN, Titolo e Autore sono obbligatori!";
    return;
  }
  try {
    await LibroService.createLibro(nuovoLibro.value);
    nuovoLibro.value = { codiceIsbn: '', titolo: '', annoPubblicazione: null, autore: { id: '' } };
    alert("Libro salvato con successo!");
    caricaDati();
  } catch (err: any) {
    console.error("Errore salvataggio:", err);
    erroreNuovoLibro.value = "Errore durante il salvataggio. Controlla la console di IntelliJ per il dettaglio!";
  }
};

onMounted(caricaDati);
</script>

<template>
  <div style="padding: 40px; font-family: sans-serif; max-width: 1000px; margin: 0 auto; background: #f9f9f9;">
    <h1>🏛️ Biblioteca - Pannello Gestione</h1>

    <div style="background: white; padding: 20px; border-radius: 8px; box-shadow: 0 2px 5px rgba(0,0,0,0.1); margin-bottom: 30px;">
      <h3>📚 Aggiungi un Nuovo Libro</h3>
      <div v-if="erroreNuovoLibro" style="color: red; padding: 10px; background: #fee; border-radius: 4px; margin-bottom: 10px;">{{ erroreNuovoLibro }}</div>

      <form @submit.prevent="aggiungiLibro" style="display: grid; grid-template-columns: 1fr 1fr; gap: 15px;">
        <input v-model="nuovoLibro.codiceIsbn" placeholder="Codice ISBN" style="padding: 10px;"/>
        <input v-model="nuovoLibro.titolo" placeholder="Titolo Libro" style="padding: 10px;"/>
        <input v-model="nuovoLibro.annoPubblicazione" type="number" placeholder="Anno Pubblicazione" style="padding: 10px;"/>
        <select v-model="nuovoLibro.autore.id" style="padding: 10px;">
          <option value="" disabled selected>Seleziona Autore</option>
          <option v-for="a in listaAutori" :key="a.id" :value="a.id">{{ a.nome }} {{ a.cognome }}</option>
        </select>
        <button type="submit" style="grid-column: span 2; padding: 12px; background: #0288d1; color: white; border: none; cursor: pointer; border-radius: 4px;">
          Salva Libro 💾
        </button>
      </form>
    </div>

    <table style="width: 100%; background: white; border-collapse: collapse;">
      <thead>
      <tr style="background: #eee;">
        <th style="padding: 10px; text-align: left;">ISBN</th>
        <th style="padding: 10px; text-align: left;">Titolo</th>
        <th style="padding: 10px; text-align: left;">Autore</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="l in listaLibri" :key="l.codiceIsbn" style="border-bottom: 1px solid #eee;">
        <td style="padding: 10px;">{{ l.codiceIsbn }}</td>
        <td style="padding: 10px;">{{ l.titolo }}</td>
        <td style="padding: 10px;">{{ l.autore?.nome }} {{ l.autore?.cognome }}</td>
      </tr>
      </tbody>
    </table>
  </div>
</template>