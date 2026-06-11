import UtenteRepository from "../repositories/UtenteRepository";

export default class UtenteService {
    static async getAllUtenti() {
        return await UtenteRepository.getAllUtenti();
    }


    static async createUtente(utente: any) {
        return await UtenteRepository.createUtente(utente);
    }

    static async deleteUtente(id: number) {
        await UtenteRepository.deleteUtente(id);
    }
}