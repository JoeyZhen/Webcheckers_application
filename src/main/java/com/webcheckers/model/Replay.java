package com.webcheckers.model;

import java.util.ArrayList;

    public class Replay {
        private int size;
        private int index;
        private String player1;
        private String player2;
        private Player winner;
        private ArrayList<Move> moves;

        public Replay() {
            size = 0;
            index = 0;
            moves = new ArrayList<>();
        }

        public void addTurn(Move m) {
            moves.add(m);
            size++;
        }

        public void setPlayers(String p1, String p2) {
            player1 = p1;
            player2 = p2;
        }

        public void setWinner(Player winner) {
            this.winner = winner;
        }

        public String getPlayer1() {
            return player1;
        }

        public String getPlayer2() {
            return player2;
        }

        public int getNumTurns() {
            return size;
        }

        public ArrayList<Move> getMoves() {
            return moves;
        }


        public Move getNextMove() {
            this.index++;
            return moves.get(this.index);
        }

        public Move getPreviousMove() {
            this.index--;
            return moves.get(this.index);
        }

        public int getIndex() {
            return this.index;
        }

        public int getSize() {
            return this.size;
        }

        public Player getWinner() {
            return winner;
        }

        @Override
        public boolean equals(Object obj) {
            Replay replay = (Replay) obj;
            if ((replay.getPlayer1().equals(player1)) && (replay.getPlayer2().equals(player2))
                    && (replay.getNumTurns() == size) && (replay.getMoves().equals(moves))) {
                return true;
            }
            return false;
        }

        @Override
        public int hashCode() {
            return (player1.hashCode() - player2.hashCode()) * size;
        }
    }
