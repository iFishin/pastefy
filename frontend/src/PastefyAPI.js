import {Cajax} from "cajaxjs";

// import store from './store'

export class PastefyAPI extends Cajax {
    constructor(baseURL = "https://pastefy.app") {
        super(baseURL)

        this.promiseInterceptor = async res => {
            const json = await res.json()

            if (json.error)
                throw json

            return json
        }
    }

    createPaste(data) {
        return this.post("/api/v2/paste", data).then(res => res.paste)
    }

    editPaste(id, data) {
        return this.put(`/api/v2/paste/${id}`, data).then(() => null)
    }

    logout() {
        localStorage.removeItem("session")
        window.location = "/"
    }
}