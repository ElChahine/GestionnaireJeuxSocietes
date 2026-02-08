<script setup>

const model = defineModel();

</script>


<template>
    <div class ="search-container">
        <input
            v-model="model"
            type="text"
            placeholder="entrez un nom de pokemon..."
            class = "search-input"
        >   
    </div>
</template>

<style>

.search-input {
  width: 100%;
  padding: 10px;
  margin-bottom: 20px;
  border-radius: 8px;
  border: 1px solid #ccc;
}

</style>