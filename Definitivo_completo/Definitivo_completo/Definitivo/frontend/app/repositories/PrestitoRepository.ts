import axios from 'axios';

const API_URL = 'http://localhost:8080/prestiti';

export default class PrestitoRepository {

  static async findAll() {
    return axios.get(API_URL);
  }

  static async attiva(idUtente: number, nomeUtente: string, cognomeUtente: string, idCopia: number) {
    return axios.post(API_URL + '/attiva', null, {
      params: { idUtente, nomeUtente, cognomeUtente, idCopia }
    });
  }

  static async restituisci(idPrestito: number) {
    return axios.post(API_URL + '/restituisci', null, {
      params: { idPrestito }
    });
  }

  static async delete(idPrestito: number) {
    return axios.delete(API_URL + '/' + idPrestito);
  }
}
