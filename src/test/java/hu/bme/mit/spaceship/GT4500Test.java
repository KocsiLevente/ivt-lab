package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class GT4500Test {

  private GT4500 ship;
  private GT4500 testShip;
  private TorpedoStore mockTS;
  private TorpedoStore mockTSPrimary;
  private TorpedoStore mockTSSecondary;

  @BeforeEach
  public void init() {
    this.mockTS = mock(TorpedoStore.class);
    this.mockTSPrimary = mock(TorpedoStore.class);
    this.mockTSSecondary = mock(TorpedoStore.class);
    this.ship = new GT4500(mockTS, mockTS);
    this.testShip = new GT4500(mockTSPrimary, mockTSSecondary);
  }

  @Test
  public void fireTorpedo_Single_Success(){
    // Arrange
    when(mockTS.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(mockTS, times(1)).fire(1);
    //assertEquals(true, result);
  }

  @Test
  public void fireTorpedo_All_Success() {
    // Arrange
    when(mockTS.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    verify(mockTS, times(2)).fire(1);
    //assertEquals(true, result);
  }

  @Test
  public void fireTorpedo_Single_PrimaryFiredLast_Fire_Secondary_Success(){
    //Arrange
    when(mockTSPrimary.fire(1)).thenReturn(true);
    when(mockTSSecondary.fire(1)).thenReturn(true);

    //Act
    testShip.fireTorpedo(FiringMode.SINGLE);
    boolean result = testShip.fireTorpedo(FiringMode.SINGLE);

    //Assert & Verify
    assertEquals(true, result);
    verify(mockTSPrimary, times(1)).fire(1);
    verify(mockTSSecondary, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_Single_PrimaryFiredLast_Fire_Primary_Success(){
    //Arrange
    when(mockTSPrimary.fire(1)).thenReturn(true);
    when(mockTSSecondary.isEmpty()).thenReturn(true);

    //Act
    testShip.fireTorpedo(FiringMode.SINGLE);
    boolean result = testShip.fireTorpedo(FiringMode.SINGLE);

    //Assert & Verify
    assertEquals(true, result);
    verify(mockTSPrimary, times(2)).fire(1);
    verify(mockTSSecondary, times(0)).fire(1);
  }

  @Test
  public void fireTorpedo_Single_SecondaryFiredLast_Fire_Primary_Success(){
    //Arrange
    when(mockTSPrimary.fire(1)).thenReturn(true);
    when(mockTSSecondary.fire(1)).thenReturn(true);

    //Act
    testShip.fireTorpedo(FiringMode.SINGLE);
    testShip.fireTorpedo(FiringMode.SINGLE);
    boolean result = testShip.fireTorpedo(FiringMode.SINGLE);

    //Assert & Verify
    assertEquals(true, result);
    verify(mockTSPrimary, times(2)).fire(1);
    verify(mockTSSecondary, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_Single_SecondaryFiredLast_Fire_Secondary_Success(){
    //Arrange
    when(mockTSPrimary.isEmpty()).thenReturn(true);
    when(mockTSSecondary.fire(1)).thenReturn(true);

    //Act
    testShip.fireTorpedo(FiringMode.SINGLE);
    boolean result = testShip.fireTorpedo(FiringMode.SINGLE);

    //Assert & Verify
    assertEquals(true, result);
    verify(mockTSPrimary, times(0)).fire(1);
    verify(mockTSSecondary, times(2)).fire(1);
  }

  @Test
  public void fireTorpedo_All_Both_Success(){
    //Arrange
    when(mockTSPrimary.fire(1)).thenReturn(true);
    when(mockTSSecondary.fire(1)).thenReturn(true);

    //Act
    boolean result = testShip.fireTorpedo(FiringMode.ALL);

    //Assert & Verify
    assertEquals(true, result);
    verify(mockTSPrimary, times(1)).fire(1);
    verify(mockTSSecondary, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_All_Primary_Success(){
    //Arrange
    when(mockTSPrimary.fire(1)).thenReturn(true);
    when(mockTSSecondary.isEmpty()).thenReturn(true);

    //Act
    boolean result = testShip.fireTorpedo(FiringMode.ALL);

    //Assert & Verify
    assertEquals(true, result);
    verify(mockTSPrimary, times(1)).fire(1);
    verify(mockTSSecondary, times(0)).fire(1);
  }
}
