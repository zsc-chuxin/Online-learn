import { createStore } from 'vuex';
import createPersistedState from 'vuex-persistedstate';

export default createStore({
    state: {
        id: '',
        username: '',
        sex: '',
        phone: '',
        email: '',
        address: '',
        token: '',
        isLogged: false,
        role: 'user'
    },
    mutations: {
        setId(state, id) {
            state.id = id;
        },
        setUserName(state, username) {
            state.username = username;
        },
        setSex(state, sex) {
            state.sex = sex;
        },
        setPhone(state, phone) {
            state.phone = phone;
        },
        setEmail(state, email) {
            state.email = email;
        },
        setAddress(state, address) {
            state.address = address;
        },
        setToken(state, token) {
            state.token = token;
        },
        clearData(state) {
            state.username = '';
            state.token = '';
            state.isLogged = false;
        },
        setIsLogged(state, isLogged) {
            state.isLogged = isLogged;
        },
        setRole(state, role) {
            state.role = role;
        }
    },
    actions: {
    },
    modules: {
    },
    plugins: [createPersistedState()]
});
