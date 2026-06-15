import axios from "axios";

export default class AutoreRepository {
    private static API_URL = "http://localhost:8080/autori";

    static async getAllAutori() {
        const response = await axios.get(this.API_URL);
        return response.data;
    }

    // NUOVO: Inserisce un autore
    static async createAutore(autore: any) {
        const response = await axios.post(this.API_URL, autore);
        return response.data;
    }

    // NUOVO: Elimina un autore tramite ID
    static async deleteAutore(id: number) {
        await axios.delete(`${this.API_URL}/${id}`);
    }
}