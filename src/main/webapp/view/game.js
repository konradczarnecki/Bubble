
const game = Vue.component('game', {

    template : `
            <div class="game">
                <div class="outline"></div>
                <main-bubble :key="bubble.id" :size="bubble.progress" :max="bubble.max"
                              @update="update" :expires="bubble.expires"></main-bubble>
            </div>`,

    data : function () {
        return {
            bubble : {},
            balance : Number(sessionStorage.getItem('balance')),
        }
    },

    mounted : function () {
        this.update();
    },

    methods : {

        update : function () {
            axios.request({
                url : '/gamestate',
                method : 'get',
                headers : {
                    token : sessionStorage.getItem('token')
                }
            }).then(response => {

                let newBubble = response.data.bubble;
                console.log(newBubble);

                if(newBubble.id != this.bubble.id) this.bubble = newBubble;
                else setTimeout(this.update, 600);
            })
        }
    }
});

Vue.component('main-bubble', {

    template : `<div v-show="visible" id="mainBubble"></div>`,

    props : ['size', 'max', 'expires'],

    data : function () {
        return {
            visible : true
        }},

    mounted : function () {

        let start = this.size / 100;
        let end = this.max / 100;
        let duration = this.expires - Date.now();
        this.visible = true;

        anime({
            targets : '#mainBubble',
            scale : [start, end],
            duration : duration,
            easing : 'linear',
            complete: () => {
                this.visible = false;
                this.$emit('update');
            }
        })
    }
});
