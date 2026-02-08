<template>
    <div 
        :class="['carte', pokemon.type.toLowerCase()]"
        @click="cliquerSurCarte"
    >
        <img :src="pokemon.image" :alt="pokemon.nom">
        <p>{{ pokemon.nom }}</p>
        <p class="badge"> {{ pokemon.type }}</p>
        <slot></slot>
    </div>
</template>

<script setup>
import { ref } from 'vue';

ref

const props = defineProps({
    pokemon: Object
});

const emit = defineEmits(['select']);

const cliquerSurCarte = () => {
    emit('select', props.pokemon);
};

</script>

<style>
.carte {
    width: 200px;
    border-radius: 15px;
    padding: 20px;
    text-align: center;
    box-shadow: 0 8px 15px rgba(0,0,0,0.1);
    transition: all 0.3s ease;
    cursor: pointer; /* Indique que c'est cliquable */
    margin: 10px;
    display: inline-block;
    color: #2c3e50;
}

.carte:hover {
    transform: translateY(-5px); /* Petit saut vers le haut */
    box-shadow: 0 12px 20px rgba(0,0,0,0.2);
}

.carte img {
    width: 120px;
    height: 120px;
    filter: drop-shadow(2px 4px 6px rgba(0,0,0,0.2));
}

.badge {
    border-radius: 12px;
    padding: 5px 15px;
    font-size: 0.9em;
    text-transform: uppercase;
    background: rgba(255, 255, 255, 0.5);
    display: inline-block;
    margin-top: 10px;
}

/* Gradients par type */
.electrik { background: linear-gradient(135deg, #fdfd96, #f6d365); }
.plante   { background: linear-gradient(135deg, #96fd96, #84fab0); }
.feu      { background: linear-gradient(135deg, #fd9696, #ff9a9e); }
.eau      { background: linear-gradient(135deg, #0cdfff, #a1c4fd); }
.normal   { background: linear-gradient(135deg, #f5f7fa, #c3cfe2); }
</style>
