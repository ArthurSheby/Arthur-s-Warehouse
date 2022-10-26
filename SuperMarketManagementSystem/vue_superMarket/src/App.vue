<template>
  <div id="app">
    <router-view/><!--    <GeneralFramework/>-->
  </div>
</template>

<script>
// import GeneralFramework from "@/components/GeneralFramework";
export default {
  name: 'App',
  components: {
    // GeneralFramework,
  },
  data(){
    return{

    }
  },
  methods:{

  },
  mounted() {

  },
}
</script>

<style scoped>
#app{
  min-height: 100vh;
}
</style>


