<template>
  <div id="app">
    <PageHeader/>
    <PageBody/>
    <PageFooter/>
  </div>
</template>

<script>
import PageHeader from '../../components/PageHeader.vue'
import PageFooter from '../../components/PageFooter.vue'
import PageBody from '../../components/PageBody.vue'

export default {
  name: 'Home',
  components: {
    PageHeader,
    PageFooter,
    PageBody
  }
}
</script>

<style>
*{
  margin: 0;
  padding: 0;
  list-style-type: none;
  text-decoration: none;
  font-family: source-han-sans-simplified-c, sans-serif;
  font-weight: 400;
  -moz-user-select: none;
  -webkit-user-select: none;
  user-select: none;
  -o-user-select: none;
  -ms-user-select: none;
}
</style>
