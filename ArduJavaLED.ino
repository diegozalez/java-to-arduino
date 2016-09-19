/*This code recive from java informacion trough serial port
 * to cange the state of an individual LED or set a mode.
 * First recive a number from 0 to 255 
 * that indicates the intensity of the red 
 * follow by the same range of numbers but for the blue intesitive
 * and the same for the green
 * The 4th number that recive is the position of the LED to change state 
 * The 5th is a number to from 0 to 255 that indicate the brightness of all LED
 * The last number to recive is the mode
 * Then if the mode is 1 modify the LED or if the mode is 2 use the rainbow method
*/
#include <Wire.h>
#include <Adafruit_NeoPixel.h>
#define NEO 6 //***CHAGE THIS FOR THE PIN YOU CONECT YOUR LED's***
#define numPixel 24//***CHAGE THIS FOR THE NUMBER OF LED's***
Adafruit_NeoPixel LED = Adafruit_NeoPixel(numPixel, NEO, NEO_GRB + NEO_KHZ800);//inicialice the LED's
int Byte_entrada = 0;//store the last message send
int a = 0;//keep count of the number of package send
int ALED [6];//store R, G, B, number of pixel, brightnes, mode
void setup() {//set up
  Serial.begin(9600);
  Serial.println("Flash debug");//for testing
  LED.begin();//inicialice all the LED's
  LED.show();//turn all off
  LED.setBrightness(30);
  rainbow(6);//animation in the beginning
  for(int d=255;d>=0;d--){//turn all LED off
    for(int i=0; i<LED.numPixels(); i++) {
      LED.setPixelColor(i,d,0,0);
    }
    LED.show();
   delay(7);
  }
  LED.setBrightness(255);

  LED.show();
}

void loop() {
  if (Serial.available() > 0) {
    Byte_entrada = Serial.read();

    if (a == 0) { //1st package number from 0 to 255
                  //indicates the intesity of red to form a RGB color
      ALED[a] = Byte_entrada;//save it in ALED array
      a++;
      Serial.print(Byte_entrada);
    }
    else if (a == 1) { //2nd package number from 0 to 255
                       //indicates the intesity of green to form a RGB color
      ALED[a] = Byte_entrada;//save it in ALED array
      a++;
      Serial.print("\t");
      Serial.print(Byte_entrada);
    }
    else if (a == 2) {//3rd package number from 0 to 255
                      //indicates the intesity of blue to form a RGB color
      ALED[a] = Byte_entrada;//save it in ALED array
      a++;
      Serial.print("\t");
      Serial.print(Byte_entrada);
    }
    else if (a == 3) {//4th package number from 0 to the number of LED's
                      //indicates the LED to modify
      ALED[a] = Byte_entrada;//save it in ALED array
      a++;
      Serial.print("\t");
      Serial.print(Byte_entrada);
    }
     else if (a == 4) {//5th package number from 0 to 255
                       //indicates the brightnes of all the LED's
      ALED[a] = Byte_entrada;//save it in ALED array
      a++;
      Serial.print("\t");
      Serial.print(Byte_entrada);
    }
    else if (a == 5) {//6th package number from 1 to 2
                      //indicates the mode in this case there is only 2 modes
                      //this part also sends to the specific LED
      ALED[a] = Byte_entrada;//save it in ALED array
      a = 0;// reset for next LED to change
      Serial.print("\t");
      Serial.print(Byte_entrada);
      Serial.println();
  if(ALED[5]==2){// in case the mode is 2
    LED.setBrightness(ALED[4]);
    LED.show();
    while(Serial.available() == 0){
    rainbow(20);
    }
    for(int d=255;d>=0;d--){
    for(int i=0; i<LED.numPixels(); i++) {
      LED.setPixelColor(i,d,0,0);
    }
    LED.show();
   delay(7);
  }
    
  }
    else if(ALED[5]==3){//for case 3
    LED.setBrightness(ALED[4]);
    LED.show();
    while(Serial.available() == 0){

    //here the code for the new mode
    
    }
    for(int d=255;d>=0;d--){
    for(int i=0; i<LED.numPixels(); i++) {
      LED.setPixelColor(i,d,0,0);
    }
    LED.show();
   delay(7);
  }
    
  }
  
  else if(ALED[5]==1){//mode 1 for modify one LED
        LED.setPixelColor( ALED[3], ALED[0], ALED[1], ALED[2]);//change LED state
        LED.setBrightness(ALED[4]);//chage the brightnes to all LED
        LED.show();//set the changes
  }
    
    }
  }

}
void rainbow(uint8_t wait) {//for the 2nd mode this is from the neopixel libray example
  uint16_t i, j;

  for(j=0; j<256; j++) {
    for(i=0; i<LED.numPixels(); i++) {
    LED.setPixelColor(i, Wheel((i+j) & 255));
    }
    LED.show();
    delay(wait);
  }
}
uint32_t Wheel(byte WheelPos) {//for the 2nd mode this is from the neopixel libray example
  WheelPos = 255 - WheelPos;
  if(WheelPos < 85) {
    return LED.Color(255 - WheelPos * 3, 0, WheelPos * 3);
  }
  if(WheelPos < 170) {
    WheelPos -= 85;
    return LED.Color(0, WheelPos * 3, 255 - WheelPos * 3);
  }
  WheelPos -= 170;
  return LED.Color(WheelPos * 3, 255 - WheelPos * 3, 0);
}

