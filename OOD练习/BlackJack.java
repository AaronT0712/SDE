public class BlackJack {
    private List<Player> players;
    private Dealer dealer;
    private List<Integer> cards;

    public BlackJack {
        this.players = new ArrayList<>();
        this.dealer = new Dealer();
        this.cards = new ArrayList<>();
    }

    // 派发下一张卡
    public int nextCard() {
        return cards.remove(0);
    }

    // 发一轮
    public void roundSend() throws Exception {
        if (this.cards.size() < this.players.size() + 1) {
            throw new Exception("No enough cards in the deck!");
        }

        for(Player person : players) {
            person.getOneCard(nextCard());
            person.makeBet(50);
        }

        dealer.getOneCard(nextCard());
    }

    public void compareResult() {
        int dealerResult = this.dealer.getBestValue();
        for(Player person : players) {
            int personResult = this.person.getBestValue();
            if (personResult > dealerResult) {
                this.dealer.lose(person.currentBet);
                person.win();
            } else {
                this.dealer.win(person.currentBet);
                person.lose();
            }
        }
    }

    // 添加player
    public void addPlayer(Player newPlayer) {
        this.players.add(newPlayer);
    }

    // 添加dealer
    public void setDealer(Dealer newDealer) {
        this.dealer = newDealer;
    }
}


@Data
public class Hand {
    private List<Integer> cards;

    public Hand() {
        cards = new ArrayList<>();
    }

    // 获取最好的分
    public int getBestValue() throws Exception {
        if (this.cards.size() == 0) {
            throw new Exception("No card in hands!");
        }

        List<Integer> results = getPossibleResults();

        int best = -1;
        for(int result : results) {
            if (best < result && result <= 21) {
                best = result;
            }
        }

        return best;
    }

    // 找到所有可能的分
    public List<Integer> getPossibleResults() {
        List<Integer> possible = new ArrayList<>();
        int oneCounts = 0, sumWithoutAce = 0;

        for(int num : cards) {
            if (num == 1) {
                oneCounts += 1;
            } else {
                sumWithoutAce += num;
            }
        }

        for(int i = oneCounts; i >= 0; i--) {
            possible.add(sumWithoutAce + oneCounts*1 + (oneCounts-i)*11);
        }

        return possible;
    }

    public void getOneCard(int card) {
        cards.add(card);
    }
}



@Data
public abstract class User {
    private int id;            // 用户的唯一标识
    private static int nextId = 1;  // 静态变量，跟踪下一个ID
    private Hand hand;         // 用户的手牌
    private int tokens;        // 用户的代币
    protected BlackJack game;  // 游戏对象

    // 构造函数：初始化手牌和代币
    public User(int initialTokens) {
        this.id = nextId++;   // 使用 自增加id
        this.hand = new Hand();
        this.tokens = initialTokens;  // 初始代币数量由子类指定
        this.game = null;
    }

    // 获取一张牌
    public void getOneCard(int card) {
        hand.getOneCard(card);
    }

    // 获取当前最佳的手牌点数
    public int getBestValue() {
        return hand.getBestValue();
    }

    // 用户赢得筹码
    public void win(int amount) {
        this.tokens += amount;
    }

    // 用户输掉筹码
    public void lose(int amount) {
        this.tokens -= amount;
    }

    // 加入一个游戏
    public void joinGame(BlackJack g) {
        this.game = g;
    }

    // // 生成 UUID
    // private String generateUUID() {
    //     return UUID.randomUUID().toString();
    // }
}

@Data
public class Dealer extends User {
    public Dealer(){
        super(10000);
    }

    public void joinGame(BlackJack g) {
        super.joinGame(g);
        g.addDealer(this);
    }
    // 其它不变
}

@Data
public class Player extends User {
    private int currentBet;

    public Player() {
        super(1000);
        this.currentBet = 0;
    }

    public void joinGame(BlackJack g) {
        super.joinGame(g);
        g.addPlayer(this);
    }

    // 重写 获胜/失败 的逻辑
    @Override
    public void win() {
        this.tokens += 2 * currentBet;
        currentBet = 0;
    }

    @Override
    public void lose() {
        currentBet = 0;
    }

    public void makeBet(int amount) throws Exception {
        if (this.tokens < amount) {
            throw new Exception("No enough tokens!");
        }

        this.currentBet = amount;
        this.tokens -= this.currentBet;
    }
}

























