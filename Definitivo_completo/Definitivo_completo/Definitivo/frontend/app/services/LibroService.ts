import LibroRepository from "../repositories/LibroRepository";

export default class LibroService {
    static async getAllLibri() {
        return await LibroRepository.getAllLibri();
    }

    static async createLibro(libro: any) {
        return await LibroRepository.createLibro(libro);
    }

    static async deleteLibro(id: number) {
        await LibroRepository.deleteLibro(id);
    }
}