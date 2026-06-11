import type { Libro } from "../models/Libro";

export default class LibroService {
    private static API_URL = "/api/libri";

    // Leggi tutti i libri (READ)
    static async findAll(): Promise<Libro[]> {
        return await $fetch<Libro[]>(this.API_URL);
    }

    // Cerca un libro per ID (READ)
    static async findById(id: number): Promise<Libro> {
        return await $fetch<Libro>(`${this.API_URL}/${id}`);
    }

    // Salva un nuovo libro (CREATE)
    static async save(libro: Libro): Promise<Libro> {
        return await $fetch<Libro>(this.API_URL, {
            method: "POST",
            body: libro
        });
    }

    // Aggiorna un libro esistente (UPDATE)
    static async update(libro: Libro): Promise<Libro> {
        return await $fetch<Libro>(`${this.API_URL}/${libro.id}`, {
            method: "PUT",
            body: libro
        });
    }

    // Elimina un libro (DELETE)
    static async delete(id: number): Promise<void> {
        await $fetch<void>(`${this.API_URL}/${id}`, {
            method: "DELETE"
        });
    }
}