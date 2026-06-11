import axios from "axios";

export default class LibroRepository {
    private static API_URL = "http://localhost:8080/libri";

    static async getAllLibri() {
        const response = await axios.get(this.API_URL);
        return response.data;
    }

    // INSERISCE UN NUOVO LIBRO
    static async createLibro(libro: any) {
        const response = await axios.post(this.API_URL, libro);
        return response.data;
    }

    // ELIMINA UN LIBRO DAL DATABASE
    static async deleteLibro(id: number) {
        await axios.delete(`${this.API_URL}/${id}`);
    }
}