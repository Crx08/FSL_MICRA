import axios from "axios";

export default class UtenteRepository {
    private static API_URL = "http://localhost:8080/utenti";

    static async getAllUtenti() {
        const response = await axios.get(this.API_URL);
        return response.data;
    }

    // NUOVO: Invia i dati
    static async createUtente(utente: any) {
        const response = await axios.post(this.API_URL, utente);
        return response.data;
    }

    static async deleteUtente(id: number) {
        await axios.delete(`${this.API_URL}/${id}`);
    }
}