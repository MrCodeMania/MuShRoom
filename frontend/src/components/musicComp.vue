<script>

export default {
    name:'musicComp',
    props: ['id', 'title', 'url', 'options'],
    template: '<div><h2>{{this.id}}, {{this.title}}, {{this.url}}, {{this.options}}</h2></div>' // img들 들어가기
    
}


</script>