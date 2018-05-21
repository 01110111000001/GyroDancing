import controlP5.*; 
import hypermedia.net.*;

UDP udp;

ControlP5 cP5, cP5a;

//variables utiles pour UDP
String remoteIP="192.168.1.100";
int remotePort =8888;  //port de transmition 
int i=0;
byte order[][]= new byte[4][4];

void setup()
{
 frameRate(60); //Nombre de rappel de la fonction setup
 
 udp = new UDP(this,8888);
 udp.log(false); //imprime l'activité de connexion
 udp.listen(true); //Attend pour l'arrivée d'un message
 
 size(800,800);
 
 //Création GUI
 cP5 = new ControlP5(this);
 
  cP5.addBang("up")
     .setId(1)
     .setPosition(200,325)
     .setImage(loadImage("flèchehaut.png"))
     .updateSize()
     ;

  cP5.addBang("Down")
     .setId(2)
     .setPosition(200,625)
     .setImage(loadImage("flèchebas.png"))
     .updateSize()
     ;
     
  cP5.addBang("Right")
     .setId(3)
     .setPosition(350,475)
     .setImage(loadImage("flèchedroite.png"))
     .updateSize()
     ;
     
  cP5.addBang("Left")
     .setId(4)
     .setPosition(50,475)
     .setImage(loadImage("flèchegauche.png"))
     .updateSize()
     ;
     
  cP5.addBang("Macarena")
     .setId(10)
     .setPosition(600,350)
     .setImage(loadImage("Danse1.png"))
     .updateSize()
      ;
      
  cP5.addBang("Chatchatchat")
     .setId(11)
     .setPosition(600,500)
     .setImage(loadImage("Danse2.png"))
     .updateSize()
     ;
     
  cP5.addBang("Rock'n'Roll")
     .setId(12)
     .setPosition(600,650)
     .setImage(loadImage("Danse3.png"))
     .updateSize()
     ;
  cP5.addButton("")
     .setPosition(0,-50)
     .setImage(loadImage("GYRO_NEGATIF.png"))
     .updateSize()
     ;
cP5a = new ControlP5(this);
  cP5a.addSlider("Vitesse_du_moteur", 0, 100, 50, 100, 200, 600, 100) //(theName, theMin, theMax, theDefaultValue, theX, theY, theW, theH)   
      //.setRange(0,100)
      .setScrollSensitivity(0.5)
      .setColorForeground(color(255))
      .setColorActive(color(87,87,87))
      .setColorBackground(color(0))
      .setId(5) 
      
      ;


}


void draw()
{
background(0);
}

void ControlEvent(ControlEvent theEvent) {
  if (theEvent.isAssignableFrom(Textfield.class) || theEvent.isAssignableFrom(Toggle.class) || theEvent.isAssignableFrom(Button.class) || theEvent.isAssignableFrom(Bang.class) || theEvent.isAssignableFrom(Slider.class)) {
    String parameter = theEvent.getName();
    String value = "";
 
}}

void keyPressed() {
  if (key == CODED) {
    if (keyCode == UP) {
      byte[] message = new byte[3];
      message[0] = 118;
      message[1] = byte(100+cP5a.getController("Vitesse_du_moteur").getValue());
      message[2] = 0;
      udp.send(message, remoteIP, remotePort);
    } else if (keyCode == DOWN) {
      byte[] message = new byte[3];
      message[0] = 118;
      message[1] = byte(100-cP5a.getController("Vitesse_du_moteur").getValue());
      message[2] = 0 ;
      udp.send(message, remoteIP, remotePort);
    } else if (keyCode == LEFT) {
      byte[] message = new byte[3];
      message[0] = 118;
      message[1] = 0;
      message[2] =byte(100+cP5a.getController("Vitesse_du_moteur").getValue()) ;
      udp.send(message, remoteIP, remotePort);
    } else if (keyCode == RIGHT) {
      byte[] message = new byte[3];
      message[0] = 118;
      message[1] = 0;
      message[2] = byte(100-cP5a.getController("Vitesse_du_moteur").getValue()); 
      udp.send(message, remoteIP, remotePort);
    }
  }
}
/*
void ControlPad(ControlEvent theEvent) {

while (i < order.length) 
{
      if(theEvent.getController().getId()==1){
    byte[] message = new byte[3];
      message[0] = 118;
      message[1] = byte(100+cP5a.getController("Vitesse_du_moteur").getValue());
      message[2] = 0;
     udp.send(message, remoteIP, remotePort);
     order[0][i]= byte(100+cP5a.getController("Vitesse_du_moteur").getValue());
     order[1][i] = 0;
     
    }
    
    if(theEvent.getController().getId()==2){
    byte[] message = new byte[3];
      message[0] = 119;
      message[1] = byte(100-cP5a.getController("Vitesse_du_moteur").getValue());
      message[2] = 0 ;
      udp.send(message, remoteIP, remotePort);
    order[0][i] =  byte(100-cP5a.getController("Vitesse_du_moteur").getValue());
    order[1][i] = 0;
    }
    
    if(theEvent.getController().getId()==3){
     byte[] message = new byte[3];
      message[0] = 121;
      message[1] = 0;
      message[2] = byte(100-cP5a.getController("Vitesse_du_moteur").getValue()); 
      udp.send(message, remoteIP, remotePort);
    order[0][i] =0;
    order [1][i] = byte(100-cP5a.getController("Vitesse_du_moteur").getValue());
    }
    if(theEvent.getController().getId()==4){
    byte[] message = new byte[3];
      message[0] = 120;
      message[1] = 0;
      message[2] =byte(100+cP5a.getController("Vitesse_du_moteur").getValue()) ;
      udp.send(message, remoteIP, remotePort); }
      order[0][i] = 0;
      order[1][i]=  byte(100+cP5a.getController("Vitesse_du_moteur").getValue());

}i++;

}
for(int i=0 ; i<order.length ; i++)
{
  byte[] message =new byte[2];
 message[0]= order[0][i];
 message[1]= order[1][i];
udp.send(message,remoteIP,remotePort);
}
    */
    //Danses
    
   if(theEvent.getController().getId()==10){
     byte[] message = new byte[1];
     message[0] = 102;
    udp.send(message,remoteIP,remotePort);}
    
   if(theEvent.getController().getId()==11){
      byte[] message = new byte[1];
     message[0] = 103;
    udp.send(message,remoteIP,remotePort);}
    
   if(theEvent.getController().getId()==12){
      byte[] message = new byte[1];
     message[0] = 104;
    udp.send(message, remoteIP,remotePort);}
    
}
