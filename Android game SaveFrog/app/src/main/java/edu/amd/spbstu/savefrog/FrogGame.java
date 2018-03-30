package edu.amd.spbstu.savefrog;

import static edu.amd.spbstu.savefrog.FrogGame.Leaf.GROWTH_SPEED;
import static edu.amd.spbstu.savefrog.FrogGame.Leaf.LEAF_SIZE;
import static edu.amd.spbstu.savefrog.FrogGame.Leaf.LEAF_SIZE_MIN;

public class FrogGame {
    public static final int GAME_STATUS_PLAY = 1;
    public static final int GAME_STATUS_WIN = 2;
    public static final int GAME_STATUS_LOSE = 3;
    int width, height;
    boolean isBuilt = false;
    Levels curLevel;
    Hero hero;

    int gameStatus;

    public FrogGame() {
    }

    public FrogGame BuildGame(V2d canvasSize) {
        width = canvasSize.x;
        height = canvasSize.y;
        this.hero = new Hero();
        this.isBuilt = true;
        return this;
    }

    public FrogGame createLevel(int level) {
        this.curLevel = new Levels(level);
        return this;
    }

    public static double distance(V2d p1, V2d p2) {
        return Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2));
    }

    public void setGameStatus(int gameStatus) {
        this.gameStatus = gameStatus;
    }

    public int getGameStatus() {
        return gameStatus;
    }

    public int checkEndOfGame() {
        int res = curLevel.isGameOver(hero.pos);
        return res;
    }

    public FrogGame createHero(V2d startPos) {
        this.hero.createHero(startPos);
        return this;
    }

    class Hero {
        V2d pos;
        V2d moveTo;
        V2d moveFrom;
        public double jump_dist;
        boolean isMoving = false;
        private float lambda = 0;

        double a, b, c;

        public Hero() {
        }

        public void createHero(V2d startPos) {
            this.pos = startPos;
            jump_dist = width / 2;
        }

        public void setJump_dist(double jump_dist) {
            this.jump_dist = jump_dist;
        }

        public void setPos(V2d pos) {
            this.pos = pos;
        }

        public void moveTo(V2d posTo) {
            double dist = distance(posTo, this.pos);
            if(dist < this.jump_dist) {
                isMoving = true;
                moveTo = posTo;
                moveFrom = new V2d(this.pos.x, this.pos.y);
                createParabola();
            }
        }

        private void createParabola() {
            double y3 = (0 + (height - (height - moveFrom.y)) / 6),   x3 = Math.abs(moveTo.x + moveFrom.x) / 2; //так координата у растёт сверху вниз
            double y1 = moveTo.y, x1 = moveTo.x;
            double y2 = moveFrom.y,   x2 = moveFrom.x;
            a = (y3 - (x3 * (y2 - y1) + x2 * y1 - x1 * y2) / (x2 - x1)) / (x3 * (x3 - x1 - x2) + x1 * x2);
            b = (y2 - y1) / (x2 - x1) - a * (x1 + x2);
            c = (x2 * y1 - x1 * y2) / (x2 - x1) + a * x1 * x2;
        }



        public void moving() {
            if (lambda < 1f) {
                pos.x = (int) ((1 - lambda) * moveFrom.x + lambda * moveTo.x);
                pos.y = (int) calculateParabola(pos.x);
                //pos.y = (int) ((1 - lambda) * moveFrom.y + lambda * moveTo.y);
                lambda += 0.02;
            } else {
                lambda = 0;
                isMoving = false;
            }
        }

        private double calculateParabola(int x) {
            return a * x * x + b * x + c;
        }
    }

    class Leaf {
        public static final int LEAF_SIZE = 90;
        public static final int LEAF_SIZE_MIN = 40;
        public static final int LEAF_SIZE_MAX = 100;
        public static final double GROWTH_SPEED = 0.3;

        double growthSpeed;
        float size;
        V2d pos;
        boolean isFinish;
        boolean isBonus;
        boolean isGrow;

        public Leaf() {
            growthSpeed = GROWTH_SPEED;
            size = LEAF_SIZE;
            isFinish = false;
            isBonus = false;
            isGrow = false;
        }

        public Leaf(int size, V2d pos) {
            this.size = size;
            this.pos = pos;
            isFinish = false;
            isBonus = false;
            isGrow = false;
        }

        public void setSize(float size) {
            this.size = size;
        }

        public void setPos(V2d pos) {
            this.pos = pos;
        }

        public void setFinish(boolean finish) {
            isFinish = finish;
        }

        public void setBonus(boolean bonus) {
            isBonus = bonus;
        }

        public void setGrow(boolean grow) {
            isGrow = grow;
        }

        public float getSize() {
            return size;
        }

        public V2d getPos() {
            return pos;
        }

        public boolean isFinish() {
            return isFinish;
        }

        public boolean isBonus() {
            return isBonus;
        }

        public boolean isGrow() {
            return isGrow;
        }

        public void setGrowthSpeed(double growthSpeed) {
            this.growthSpeed = growthSpeed;
        }

        public void changeSize() {
            if (this.isGrow()) {
                this.size += this.growthSpeed;
            } else {
                this.size -= this.growthSpeed;
            }
            this.changeGrowthIfNeed();
        }

        public void changeGrowthIfNeed() {
            if (size < LEAF_SIZE_MIN) {
                this.setGrow(true);
                return;
            }
            if (size > Leaf.LEAF_SIZE_MAX) {
                this.setGrow(false);
                return;
            }
        }
    }

    class Levels {
        public static final int LEVEL_FIRST = 0;
        public static final int LEVEL_SECOND = 1;
        public static final int LEVEL_THIRD = 2;
        public static final int LEVEL_FOURTH = 3;
        private static final int MAX_LEAF_COUNT = 10;

        int leafCount = MAX_LEAF_COUNT;
        Leaf[] leafs;
        int curLevel;
        public int maxScore = 0;
        public int bonusScore = 0;

        public Levels(int curLevel) {
            leafs = new Leaf[leafCount];
            for (int i = 0; i < leafCount; i++) {
                leafs[i] = new Leaf();
                if (i % 2 == 0) {
                    leafs[i].setGrow(true);
                } else {
                    leafs[i].setGrow(false);
                }
            }

            if (!(curLevel <= 0 || curLevel >= 4)) {
                this.curLevel = curLevel;
                //this.curLevel = LEVEL_FIRST;
            } else {
                this.curLevel = LEVEL_FIRST;
            }
            switch (this.curLevel) {
                case 0:
                    leafCount = 3;

                    leafs[0].setPos(new V2d(width / 6, height / 2));
                    leafs[1].setPos(new V2d(width / 2, height / 2));
                    leafs[1].setSize(LEAF_SIZE * 1.5f);
                    leafs[1].setGrowthSpeed(0);
                    leafs[2].setPos(new V2d(width * 5 / 6, height / 2));
                    leafs[2].setFinish(true);
                    maxScore = bonusScore * 0;

                    hero.setJump_dist(width / 2);
                    break;
                case 1:
                    leafCount = 3;

                    leafs[0].setPos(new V2d(width / 6, height / 2));
                    leafs[1].setPos(new V2d(width / 2, height / 2));
                    leafs[1].setSize(LEAF_SIZE * 1.5f);
                    leafs[1].setGrowthSpeed(GROWTH_SPEED * 2);
                    leafs[2].setPos(new V2d(width * 5 / 6, height / 2));
                    leafs[2].setFinish(true);
                    maxScore = bonusScore * 0;

                    hero.setJump_dist(width / 2);
                    break;
                case 2:
                    leafCount = 4;

                    leafs[0].setPos(new V2d(width / 6, height / 2));
                    leafs[0].setSize(2 * LEAF_SIZE_MIN);
                    leafs[1].setPos(new V2d(width / 2, height / 4));
                    leafs[1].setSize(1.5f * LEAF_SIZE_MIN);
                    leafs[1].setBonus(true);
                    leafs[1].setGrowthSpeed(GROWTH_SPEED * 2);
                    leafs[2].setPos(new V2d(width / 2, height * 3 / 4));
                    leafs[2].setSize(2 * LEAF_SIZE_MIN);
                    leafs[3].setPos(new V2d(width * 5 / 6, height / 2));
                    leafs[3].setFinish(true);
                    bonusScore = 5;
                    maxScore = bonusScore * 1;

                    hero.setJump_dist(width / 2);
                    break;
                case 3:
                    leafCount = 6;

                    leafs[0].setPos(new V2d(width / 6, height / 4));
                    leafs[1].setPos(new V2d(width / 4, height * 5 / 8));
                    leafs[1].setBonus(true);
                    leafs[2].setPos(new V2d(width * 3 / 8, height / 5));
                    leafs[3].setPos(new V2d(width / 2, height / 2));
                    leafs[3].setBonus(true);
                    leafs[3].setGrowthSpeed(GROWTH_SPEED * 2);
                    leafs[4].setPos(new V2d(width * 2 / 3, height / 3));
                    leafs[5].setPos(new V2d(width * 5 / 6, height / 2));
                    leafs[5].setFinish(true);
                    bonusScore = 5;
                    maxScore = bonusScore * 2;

                    hero.setJump_dist(width / 3);
                    break;
            }
        }

        boolean isWin(V2d pos) {
            if ((Math.abs(pos.x - leafs[leafCount - 1].getPos().x) <= LEAF_SIZE * 2)
                    && (Math.abs(pos.y - leafs[leafCount - 1].getPos().y) <= LEAF_SIZE * 2))
                return true;
            return false;
        }

        int isGameOver(V2d pos) {
            if (isWin(pos) == true)
                return GAME_STATUS_WIN;

            for (int i = 0; i < leafCount; i++) {
                if ((Math.abs(pos.x - leafs[i].getPos().x) <= LEAF_SIZE * 2) && (Math.abs(pos.y - leafs[i].getPos().y) <= LEAF_SIZE * 2))
                    if (leafs[i].getSize() >= LEAF_SIZE_MIN + 10) {
                        return GAME_STATUS_PLAY;
                    } else {
                        return GAME_STATUS_LOSE;
                    }
            }
            return GAME_STATUS_LOSE;
        }
    }
}
