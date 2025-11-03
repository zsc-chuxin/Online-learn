<template>
  <RouterView />
</template>

<script>

</script>
<style>
body{
  min-height: 600px;
  min-width: 1500px;
  max-width: 1550px;
  max-height: 600px;
  margin:0 auto;
  background-color: rgb(240, 240, 240);
}
.el-checkbox__input.is-checked+.el-checkbox__label {
  color: inherit!important;
}
a {
  color: #409EFF;
  text-decoration: none;
}
a:hover {
  text-decoration: underline!important;
}
a:active {
  color: #409EFF;
}
</style>