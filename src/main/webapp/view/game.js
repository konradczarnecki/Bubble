const eventBus = new Vue({});

const game = Vue.component('game', {

    template : `
            <div class="game">
                <div class="clickRim"></div>
                <div class="hoverRim" @click.left="bet" @contextmenu="redeem($event)"></div>
                <div class="outline" ></div>
                <main-bubble :key="bubble.id" :size="bubble.progress" :max="bubble.max"
                              @update="update" :expires="bubble.expires"></main-bubble>
                <p class="counter">{{multi}}</p>
                <p class="stack">{{stack}}</p>  
                <p class="prize">{{prize}}</p>
            </div>`,

    data : function () {
        return {
            bubble : {},
            balance : Number(sessionStorage.getItem('balance')),
            multi : 1,
            stack : 0,
            prize : 0,
            popped : true,
            redeemed : false
        }
    },

    mounted : function () {

        this.update();
        setInterval(this.updateLabel, 10);

        $('.hoverRim').hover(hoverAnimation(0, 0.4, 150), hoverAnimation(0.4, 0, 150));
    },
    
    watch : {
        balance : function (newValue, oldValue) {
            eventBus.$emit('updateBalance', newValue);
        },

        prize : function (newVal, oldVal) {

            $('.prize').css('visibility', 'visible');
            anime({
                targets : '.prize',
                translateY : [-50, -250],
                easing : 'linear',
                opacity : [1, 0],
                duration : 4000
            });
        }
    },

    methods : {

        update : function () {

            this.popped = true;

            axios.request({
                url : '/gamestate',
                method : 'get',
                headers : {
                    token : sessionStorage.getItem('token')
                }
            }).then(response => {

                console.log(response.data);

                this.balance = response.data.balance.value;
                let newBubble = response.data.bubble;

                if(newBubble.id != this.bubble.id){

                    this.bubble = newBubble;
                    this.popped = false;
                    this.stack = 0;
                    this.redeemed = false;

                } else setTimeout(this.update, 600);

            }).catch(error =>{
               console.log(error);
            });
        },

        updateLabel : function () {

            let toGo = (this.bubble.expires - Date.now()) / 600;
            let current = this.bubble.max - toGo;

            let m = current <= 20 ? 1 : 1 + (current - 20) * this.bubble.multiplier / 80;
            if(this.popped) this.multi = '';
            else this.multi = Number(m).toFixed(2);
        },

        bet : function () {

            if(this.redeemed) return;

            $('.clickRim').css('box-shadow', '0 0 4px 4px rgb(230, 220, 126)');
            anime({
                targets : '.clickRim',
                opacity : [0.3, 0.7],
                duration : 100,
                easing : 'easeInQuad',
                complete : function () {
                    anime({
                        targets : '.clickRim',
                        opacity : [0.8, 0],
                        duration : 200,
                        easing : 'easeOutQuad'
                    });
                }
            });

            let wager = 100;

            axios.request({
                url : '/bet',
                method : 'post',
                params : {
                    amount : wager
                }
            }).then(response => {

                console.log(response);
                if(response.data.status == 'success'){
                    this.balance -= wager;
                    this.stack += wager;
                }
            }).catch(error => {
                console.log(error);
            });
        },

        redeem : function (event) {

            event.preventDefault();
            if(this.redeemed || this.multi == 1.0 || this.multi == '') return;

            $('.clickRim').css('box-shadow', '0 0 4px 4px rgb(126, 230, 111)');
            anime({
                targets : '.clickRim',
                opacity : [0.3, 0.7],
                duration : 100,
                easing : 'easeInQuad',
            });

            axios.request({
                url : '/redeem',
                method : 'post',
                headers : {
                    token : sessionStorage.getItem('token')
                },
                params : {
                    stamp : Date.now(),
                    tkn : 'abc',
                    balance : this.balance,
                    bubble : this.bubble.id
                }
            }).then(response => {

                console.log(response);
                if(response.data.status == 'success'){
                    this.prize = response.data.item - this.balance;
                    this.balance = response.data.item;
                    this.stack = '';
                    this.redeemed = true;
                }

            }).catch(error => {
                console.log(error);
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
                this.stack = '';
                this.$emit('update');

                $('.clickRim').css('box-shadow', '0 0 4px 4px rgb(230, 230, 230)');

                anime({
                    targets : '.clickRim',
                    opacity : 0,
                    duration : 800,
                    easing: 'easeInQuad'
                });
            }
        })
    }
});

function hoverAnimation(from, to, duration){

    return function () {
        anime({
            targets: '.hoverRim',
            opacity : [from, to],
            duration : duration,
            easing : 'easeInQuad'
        })
    }
}
