// plugins/primevue.js
import { defineNuxtPlugin } from '#app'
import PrimeVue from 'primevue/config'
import Button from 'primevue/button'
import InputText from 'primevue/inputtext'
import DataTable from 'primevue/datatable'
import Column from 'primevue/column'

import 'primevue/resources/themes/lara-light-blue/theme.css' // tema
import 'primevue/resources/primevue.min.css'                 // stili base
import 'primeicons/primeicons.css'                           // icone

export default defineNuxtPlugin((nuxtApp) => {
    nuxtApp.vueApp.use(PrimeVue)

    // Registriamo alcuni componenti globalmente
    nuxtApp.vueApp.component('Button', Button)
    nuxtApp.vueApp.component('InputText', InputText)
    nuxtApp.vueApp.component('DataTable', DataTable)
    nuxtApp.vueApp.component('Column', Column)
})
