import axios from "axios";
import type { Prestito } from "../models/Prestito";

export default class PrestitoRepository {
    private static API_URL = "http://localhost:8080/api/prestiti";

    static findAll() {
        return axios.get<Prestito[]>(this.API_URL);
    }

    static findById(id: number) {
        return axios.get<Prestito>(`${this.API_URL}/${id}`);
    }

    static save(prestito: Prestito) {
        return axios.post<Prestito>(this.API_URL, prestito);
    }

    static update(prestito: Prestito) {
        return axios.put<Prestito>(`${this.API_URL}/${prestito.id}`, prestito);
    }

    static delete(id: number) {
        return axios.delete<void>(`${this.API_URL}/${id}`);
    }

    // Endpoint personalizzato per registrare la restituzione di un libro
    static restituisci(id: number) {
        return axios.put<Prestito>(`${this.API_URL}/${id}/restituzione`);
    }
}