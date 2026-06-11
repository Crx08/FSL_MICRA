import PrestitoRepository from "../repositories/PrestitoRepository";
import type { Prestito } from "../models/Prestito";

export default class PrestitoService {
    static async getAllPrestiti(): Promise<Prestito[]> {
        const response = await PrestitoRepository.findAll();
        return response.data;
    }

    static async getPrestitoById(id: number): Promise<Prestito> {
        const response = await PrestitoRepository.findById(id);
        return response.data;
    }

    static async avviaPrestito(prestito: Prestito): Promise<Prestito> {
        const response = await PrestitoRepository.save(prestito);
        return response.data;
    }

    static async updatePrestito(prestito: Prestito): Promise<Prestito> {
        const response = await PrestitoRepository.update(prestito);
        return response.data;
    }

    static async deletePrestito(id: number): Promise<void> {
        await PrestitoRepository.delete(id);
    }

    static async restituisciLibro(id: number): Promise<Prestito> {
        const response = await PrestitoRepository.restituisci(id);
        return response.data;
    }
}