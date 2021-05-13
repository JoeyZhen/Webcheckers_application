//package com.webcheckers.model;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.BeforeAll;
//import org.testng.Assert;
//
//import static com.webcheckers.model.ViewMode.PLAY;
//import static org.junit.jupiter.api.Assertions.*;
//import java.util.Iterator;
//
//
//public class BoardTest {
//  private Piece temp[]= new Piece[32];
//
//  private Board board;
//  private Piece piece = new Piece(Type.KING, Color.RED);
//  private Piece piece2 = new Piece(Type.KING, Color.WHITE);
//
//  @BeforeAll
//  private void initialize(){
//    temp[0] = piece;
//    temp[14] = piece2;
//    board=new Board(temp);
//  }
//
//  @BeforeAll
//  private void JumpInit(){
//    temp[4] = piece;
//    temp[9] = piece2;
//    board = new Board(temp);
//  }
//
//  @BeforeAll
//  private void invJumpInit(){
//    temp[0] = piece;
//    temp[4] = piece2;
//    board = new Board(temp);
//
//  }
//
//  @Test
//  private void validateMoveTest(){
//    initialize();
//
//    Message invalid = board.validateMove(new Move(new Position(0), new Position(14)), piece.getColor());
//    Assert.assertEquals(Message.ERROR, invalid.getType());
//
//    Message invalid2 = board.validateMove(new Move(new Position(0), new Position(4)), piece2.getColor());
//    Assert.assertEquals(Message.ERROR, invalid2.getType());
//
//    Message invalid3 = board.validateMove(new Move(new Position(0), new Position(-1)), piece.getColor());
//    Assert.assertEquals(Message.ERROR, invalid3.getType());
//
//    Message valid1 = board.validateMove(new Move(new Position(0), new Position(4)), piece.getColor());
//    Assert.assertEquals(Message.ERROR, valid1.getType());
//
//  }
//
//  @Test
//  private void validateSimpleJumpTest(){
//    JumpInit();
//
//    Message validJump1 = board.validateMove(new Move(new Position(4), new Position(13)), piece.getColor());
//    Assert.assertEquals(Message.INFO, validJump1.getType());
//
//    Message validJump2 = board.validateMove(new Move(new Position(9), new Position(0)), piece2.getColor());
//    Assert.assertEquals(Message.INFO, validJump2.getType());
//
//    invJumpInit();
//
//    Message invalidJump1 = board.validateMove(new Move(new Position(4), new Position(-4)), piece2.getColor());
//    Assert.assertEquals(Message.ERROR, invalidJump1.getType());
//
//    Message invalidJump2 = board.validateMove(new Move(new Position(0), new Position(9)), piece2.getColor());
//    Assert.assertEquals(Message.ERROR, invalidJump2.getType());
//
//
//  }
//
//  @Test
//  public void isGameOverTest(){
//    Piece end[]= new Piece[32];
//    end[6]= new Piece(Type.KING, Color.WHITE);
//    Board game = new Board(end);
//
//    boolean done = game.isGameOver(Color.WHITE);
//    Assert.assertFalse(done);
//
//    done = game.isGameOver(Color.RED);
//    Assert.assertTrue(done);
//  }
//
//
//  @Test
//  public void backupMoveTest(){
//
//    initialize();
//
//    Move moved=new Move(4, 1);
//   // piece2=board.makeMove(moved, Color.WHITE);
//    board.backUpMove(moved, Color.WHITE, piece2);
//
//    //Assert.assertEquals();
//
//  }
//
//  @Test
//  public void flip_board_test(){
//    Iterator iterator = board.iterator();
//
//  }
//
//
//
//
//}
