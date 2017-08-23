
const farm = Vue.component('farm', {

    template : `
            <div class="farm">
                <bubble v-for="b in bubbles" :key="b.id" :posX="b.x" :posY="b.y"></bubble>
            </div>`,

    data : function () {
        return {
            bubbles: []
        }
    },

    mounted : function () {
        setInterval(this.update, 500);
    },

    methods : {

        update : function () {

            axios.request({
                url : '/farmstate',
                method : 'get',
                headers : {
                    'token' : sessionStorage.getItem('token')
                }
            }).then(response => {
                this.bubbles = response.data.bubbles;
            }).catch(response => {
                console.log(response);
            })
        }
    },
});

Vue.component('bubble', {

    template : `
            <div style="border-radius: 100%" class="bubbleImage" :style="{gridColumn : posX, gridRow : posY}"/>`,

    props : ['posX', 'posY'],
});
