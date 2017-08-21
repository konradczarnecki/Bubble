
const game = Vue.component('game', {

    template : `
            <div class="game">
                <div class="outline"></div>
                <main-bubble :key="bubble.id" :size="bubble.progress" :max="bubble.max"
                              @update="update" :expires="bubble.expires"></main-bubble>
            </div>`,

    data : function () {
        return {
            bubble : {}
        }
    },

    mounted : function () {
        this.update();
    },

    methods : {

        update : function () {
            axios.request({
                url : '/state',
                method : 'get',
                headers : {
                    token : sessionStorage.getItem('token')
                }
            }).then(response => {

                let newBubble = response.data;
                console.log(newBubble);

                if(newBubble.id != this.bubble.id) this.bubble = newBubble;
                else setTimeout(this.update, 600);
            })
        }
    }
});

const mainBubble = Vue.component('main-bubble', {

    template : `
                <div id="mainBubble"></div>
            `,

    props : ['size', 'max', 'expires'],

    mounted : function () {

        let start = this.size / 100;
        let end = this.max / 100;
        let duration = this.expires - Date.now();

        anime({
            targets : '#mainBubble',
            scale : [start, end],
            duration : duration,
            easing : 'linear',
            complete: () => {
                this.$emit('update');
            }
        })
    }
});
