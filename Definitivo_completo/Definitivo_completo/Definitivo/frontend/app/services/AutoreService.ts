import AutoreRepository from "../repositories/AutoreRepository";

export default class AutoreService {
    static async getAllAutori() {
        return await AutoreRepository.getAllAutori();
    }

    static async createAutore(autore: any) {
        return await AutoreRepository.createAutore(autore);
    }

    static async deleteAutore(id: number) {
        await AutoreRepository.deleteAutore(id);
    }
}