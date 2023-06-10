package com.example.grafico;

import static java.lang.Math.sqrt;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

class Figuras
{ private int TipoFigura;
    private int Color;
    private Point Ini;
    private Point Fin;
    Figuras()
    { TipoFigura=0;
        Color=0;
        Ini=null;
        Fin=null;
    }
    public void setTipoFigura(int tipo)
    { TipoFigura=tipo;
    }
    public void setColor(int color)
    { Color=color;
    }
    public void setIni(Point aux)
    { Ini=aux;
    }
    public void setFin(Point aux)
    { Fin=aux;
    }
    public int getTipoFigura()
    { return TipoFigura;
    }
    public int getColor()
    { return Color;
    }
    public Point getIni()
    { return Ini;
    }
    public Point getFin()
    { return Fin;
    }
}

public class MainActivity extends AppCompatActivity implements View.OnTouchListener
{ private RelativeLayout Layout1;
    private PlanoDeDibujo  Miplano;
    private boolean PrimerPunto=true;
    private boolean SegundoPunto=false;
    private Point Ini;
    private Point Fin;
    private int FiguraSeleccionada;
    private int ColorSeleccionado;
    private int GrosorSeleccionado;
    private Rect SelectionLine;
    private Rect SelectionSquare;
    private Rect SelectionRectangle;
    private Rect SelectionPolygon;
    private Rect SelectionCircle;
    private Rect SelectionElipse;
    private Rect SelColorRojo;
    private Rect SelColorVerde;
    private Rect SelColorAzul;
    private Rect SelColorAmarillo;
    private Rect SelColorNaranja;
    private Rect SelColorMorado;
    private Rect SelGrosorDelgado;
    private Rect SelGrosorAncho;
    private Rect Fuente;
    private Rect Destino;
    private Bitmap Icono1;
    private ArrayList<Figuras> Lista;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    { super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Layout1=findViewById(R.id.layout1);
        Miplano=new PlanoDeDibujo(this);
        Miplano.setOnTouchListener(this);
        Layout1.addView(Miplano);
        Ini=new Point();
        Fin=new Point();

        //Figuras
        SelectionLine = new Rect(0,0,200,200);
        SelectionSquare= new Rect(200,0,400,200);
        SelectionRectangle = new Rect(400,0,600,200);
        SelectionPolygon = new Rect(600,0,800,200);
        SelectionCircle = new Rect(800,0,1000,200);
        SelectionElipse = new Rect(1000,0,1200,200);

        //Colores
        SelColorRojo = new Rect(0,200,200,400);
        SelColorVerde= new Rect(200,200,400,400);
        SelColorAzul = new Rect(400,200,600,400);
        SelColorAmarillo = new Rect(600,200,800,400);
        SelColorNaranja = new Rect(800,200,1000,400);
        SelColorMorado = new Rect(1000,200,1200,400);

        //Grosor
        SelGrosorDelgado=new Rect(1200,200,1400,400);
        SelGrosorAncho=new Rect(1200,0,1400,200);


        Fuente = new Rect(0,0,1117,1197);
        Destino= new Rect(800,0,1000,200);
        //Icono1 = BitmapFactory.decodeResource(getResources(),R.drawable.img);
        Lista=new ArrayList<>();
        //System.out.println("alto: "+Icono1.getHeight());
        //System.out.println("ancho: "+Icono1.getWidth());
        Button guardarButton = new Button(this);
        guardarButton.setText("Guardar");
        guardarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarArchivo();
            }
        });
        RelativeLayout.LayoutParams guardarParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        guardarParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        guardarParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        guardarParams.setMargins(0, 0, 16, 16); // Margen derecho e inferior
        Layout1.addView(guardarButton, guardarParams);

        Button leerButton = new Button(this);
        leerButton.setText("Leer");
        leerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leerArchivo();
            }
        });
        RelativeLayout.LayoutParams leerParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        leerParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        leerParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        leerParams.addRule(RelativeLayout.ABOVE, guardarButton.getId());
        leerParams.setMargins(0, 0, 16, 16); // Margen derecho e inferior
        Layout1.addView(leerButton, leerParams);
    }

    public boolean onTouch(View v, MotionEvent event)
    { //Toast.makeText(this,"Toque",Toast.LENGTH_SHORT).show();
        int Posx=(int)event.getX();
        int Posy=(int)event.getY();
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if((Posx>0)&&(Posx<200)&&(Posy>0)&&(Posy<200)) {
                    FiguraSeleccionada=1;
                    Toast.makeText(this,"Sel. Line",Toast.LENGTH_SHORT).show();
                    break;
                }
                if((Posx>200)&&(Posx<400)&&(Posy>0)&&(Posy<200)) {
                    FiguraSeleccionada=2;
                    Toast.makeText(this,"Sel. Square",Toast.LENGTH_SHORT).show();
                    break;
                }
                if((Posx>400)&&(Posx<600)&&(Posy>0)&&(Posy<200)) {
                    FiguraSeleccionada=3;
                    Toast.makeText(this,"Sel. Rectangle",Toast.LENGTH_SHORT).show();
                    break;
                }
                if((Posx>600)&&(Posx<800)&&(Posy>0)&&(Posy<200)) {
                    FiguraSeleccionada=4;
                    Toast.makeText(this,"Sel. Polygon",Toast.LENGTH_SHORT).show();
                    break;
                }
                if((Posx>800)&&(Posx<1000)&&(Posy>0)&&(Posy<200)) {
                    FiguraSeleccionada=5;
                    Toast.makeText(this,"Sel. Circle",Toast.LENGTH_SHORT).show();
                    break;
                }
                if((Posx>1000)&&(Posx<1200)&&(Posy>0)&&(Posy<200)) {
                    FiguraSeleccionada=6;
                    Toast.makeText(this,"Sel. Elipse",Toast.LENGTH_SHORT).show();
                    break;
                }



                //Seleccion de posicion de colores
                if((Posx>0)&&(Posx<200)&&(Posy>200)&&(Posy<400)) {
                    ColorSeleccionado=1;
                    Toast.makeText(this,"Sel. Color Rojo",Toast.LENGTH_SHORT).show();
                    break;
                }
                if((Posx>20)&&(Posx<400)&&(Posy>200)&&(Posy<400)) {
                    ColorSeleccionado=2;
                    Toast.makeText(this,"Sel. Color Verde",Toast.LENGTH_SHORT).show();
                    break;
                }
                if((Posx>400)&&(Posx<600)&&(Posy>200)&&(Posy<400)) {
                    ColorSeleccionado=3;
                    Toast.makeText(this,"Sel. Color Azul",Toast.LENGTH_SHORT).show();
                    break;
                }
                if((Posx>600)&&(Posx<800)&&(Posy>200)&&(Posy<400)) {
                    ColorSeleccionado=4;
                    Toast.makeText(this,"Sel. Color Amarillo",Toast.LENGTH_SHORT).show();
                    break;
                }
                if((Posx>800)&&(Posx<1000)&&(Posy>200)&&(Posy<400)) {
                    ColorSeleccionado=5;
                    Toast.makeText(this,"Sel. Color Naranja",Toast.LENGTH_SHORT).show();
                    break;
                }
                if((Posx>1000)&&(Posx<1200)&&(Posy>200)&&(Posy<400)) {
                    ColorSeleccionado=6;
                    Toast.makeText(this,"Sel. Color Morado",Toast.LENGTH_SHORT).show();
                    break;
                }

                //Seleccion del grosor del pincel
                if((Posx>1200)&&(Posx<1400)&&(Posy>200)&&(Posy<400)) {
                    GrosorSeleccionado=1;
                    Toast.makeText(this,"Sel. Delgado",Toast.LENGTH_SHORT).show();
                    break;
                }
                if((Posx>1200)&&(Posx<1400)&&(Posy>0)&&(Posy<200)) {
                    GrosorSeleccionado=2;
                    Toast.makeText(this,"Sel. Ancho",Toast.LENGTH_SHORT).show();
                    break;
                }


                if(PrimerPunto)
                { Ini.set(Posx,Posy);
                    PrimerPunto=false;
                    SegundoPunto=false; }
                break;
            case MotionEvent.ACTION_MOVE:  { Fin.set(Posx,Posy);
                SegundoPunto=true;
            }
            break;
            case MotionEvent.ACTION_UP: if(SegundoPunto) {
                Figuras Fig = new Figuras();
                Fig.setTipoFigura(FiguraSeleccionada);
                Fig.setColor(ColorSeleccionado);
                Point PuntoInicial = new Point(Ini.x,Ini.y);
                Fig.setIni(PuntoInicial);
                Point PuntoFinal= new Point(Fin.x,Fin.y);
                Fig.setFin(PuntoFinal);
                Lista.add(Fig);
            }
                PrimerPunto=true;
                SegundoPunto=false;
                break;
        }
        Miplano.invalidate();
        return true;
    }
    class PlanoDeDibujo extends View {
        public PlanoDeDibujo(Context context) {
            super(context);
        }

        protected void onDraw(Canvas canvas) {
            canvas.drawRGB(255, 255, 255);
            //canvas.drawBitmap(Icono1, Fuente, Destino, null);
            Paint PincelRect = new Paint();

            //Dibujamos recuadros de figuras
            PincelRect.setARGB(255, 0, 0, 0);
            canvas.drawRect(SelectionLine, PincelRect);
            canvas.drawRect(SelectionSquare, PincelRect);
            canvas.drawRect(SelectionRectangle, PincelRect);
            canvas.drawRect(SelectionPolygon, PincelRect);
            canvas.drawRect(SelectionCircle, PincelRect);
            canvas.drawRect(SelectionElipse, PincelRect);

            //Dibujamos recuadros de colores
            PincelRect.setARGB(255, 255, 0, 0);
            canvas.drawRect(SelColorRojo, PincelRect);
            PincelRect.setARGB(255, 0, 255, 0);
            canvas.drawRect(SelColorVerde, PincelRect);
            PincelRect.setARGB(255, 0, 0, 255);
            canvas.drawRect(SelColorAzul, PincelRect);
            PincelRect.setARGB(255, 255, 255, 0);
            canvas.drawRect(SelColorAmarillo, PincelRect);
            PincelRect.setARGB(255, 255, 112, 40);
            canvas.drawRect(SelColorNaranja, PincelRect);
            PincelRect.setARGB(255, 139, 0, 255);
            canvas.drawRect(SelColorMorado, PincelRect);

            //Dibujamos recuadros de grosor
            PincelRect.setARGB(255, 0, 0, 0);
            canvas.drawRect(SelGrosorDelgado, PincelRect);
            canvas.drawRect(SelGrosorAncho, PincelRect);

            //Dibujamos simbolos grosor
            PincelRect.setStrokeWidth(5);
            PincelRect.setARGB(255, 255, 255, 255);
            canvas.drawLine(1200, 400, 1400, 200, PincelRect);
            PincelRect.setStrokeWidth(30);
            canvas.drawLine(1220, 170, 1390, 20, PincelRect);



            //Dibujamos símbolos de figuras
            PincelRect.setStrokeWidth(20);
            PincelRect.setARGB(255, 255, 255, 255);
            canvas.drawLine(0, 200, 200, 0, PincelRect); //Línea
            canvas.drawRect(240, 40, 360, 160, PincelRect); //Cuadrado
            canvas.drawRect(430, 60, 570, 140, PincelRect); //Rectángulo
            //canvas.drawCircle(600, 100, 90, PincelRect); //Poligono
            canvas.drawCircle(900, 100, 80, PincelRect); //Círculo
            canvas.drawOval(1020, 50, 1180, 150, PincelRect); //Elipse

            Paint lapiz = new Paint();
            lapiz.setStrokeWidth(10);
            lapiz.setStyle(Paint.Style.STROKE);
            if(SegundoPunto){
                switch(ColorSeleccionado){
                    case 1: lapiz.setARGB(255, 255, 0, 0);
                        break;
                    case 2: lapiz.setARGB(255, 0, 255, 0);
                        break;
                    case 3: lapiz.setARGB(255, 0, 0, 255);
                        break;
                    case 4: lapiz.setARGB(255, 255, 255, 0);
                        break;
                    case 5: lapiz.setARGB(255, 255, 112, 40);
                        break;
                    case 6: lapiz.setARGB(255, 139, 0, 255);
                        break;
                }
                switch(FiguraSeleccionada){
                    case 1:
                        canvas.drawLine(Ini.x, Ini.y, Fin.x, Fin.y, lapiz);
                        break;
                    case 2:
                        float left = Math.min(Ini.x, Fin.x);
                        float top = Math.min(Ini.y, Fin.y);
                        float right = Math.max(Ini.x, Fin.x);
                        float bottom = Math.max(Ini.y, Fin.y);

                        canvas.drawRect(left, top, right, bottom, lapiz);
                        break;
                    case 3:
                        canvas.drawRect(Ini.x, Ini.y, Fin.x, Fin.y, lapiz);
                        break;
                    case 4:
                        Path path = new Path();
                        float centerX = (Ini.x + Fin.x) / 2;
                        float centerY = (Ini.y + Fin.y) / 2;
                        float radius = Math.abs(Fin.x - Ini.x) / 2;

                        // Calcular las coordenadas de los vértices del pentágono
                        float angle = (float) (Math.PI * 2 / 5); // Ángulo entre los vértices
                        float rotation = (float) (Math.PI / 2); // Rotación inicial para que el primer vértice esté en la parte superior

                        for (int i = 0; i < 5; i++) {
                            float x = centerX + (float) (radius * Math.cos(angle * i + rotation));
                            float y = centerY + (float) (radius * Math.sin(angle * i + rotation));

                            if (i == 0) {
                                path.moveTo(x, y);
                            } else {
                                path.lineTo(x, y);
                            }
                        }

                        path.close(); // Cierra el pentágono, dibujando una línea desde el último vértice hasta el primero

                        canvas.drawPath(path, lapiz);
                        break;
                    case 5:
                        float difX = Fin.x-Ini.x;
                        float difY = Fin.y-Ini.y;
                        float radio = (float) sqrt((difX * difX) + (difY) * (difY));
                        canvas.drawCircle(Ini.x, Ini.y, radio, lapiz);
                        break;

                    case 6:
                        canvas.drawOval(Ini.x, Ini.y, Fin.x, Fin.y, lapiz);
                        break;

                }
            }
            for(int Nf=0; Nf<Lista.size(); Nf++){
                switch(Lista.get(Nf).getColor()) {
                    case 1: lapiz.setARGB(255, 255, 0, 0);
                        break;
                    case 2: lapiz.setARGB(255, 0, 255, 0);
                        break;
                    case 3: lapiz.setARGB(255, 0, 0, 255);
                        break;
                    case 4: lapiz.setARGB(255, 255, 255, 0);
                        break;
                    case 5: lapiz.setARGB(255, 255, 112, 40);
                        break;
                    case 6: lapiz.setARGB(255, 139, 0, 255);
                        break;
                }

                switch (GrosorSeleccionado)
                {
                    case 1: lapiz.setStrokeWidth(5);
                        break;
                    case 2: lapiz.setStrokeWidth(50);
                        break;
                }


                switch(Lista.get(Nf).getTipoFigura()){
                    case 1: canvas.drawLine(Lista.get(Nf).getIni().x, Lista.get(Nf).getIni().y, Lista.get(Nf).getFin().x, Lista.get(Nf).getFin().y, lapiz);
                        break;
                    case 2:
                        float left = Math.min(Lista.get(Nf).getIni().x, Lista.get(Nf).getFin().x);
                        float top = Math.min(Lista.get(Nf).getIni().y, Lista.get(Nf).getFin().y);
                        float right = Math.max(Lista.get(Nf).getIni().x, Lista.get(Nf).getFin().x);
                        float bottom = Math.max(Lista.get(Nf).getIni().y, Lista.get(Nf).getFin().y);

                        canvas.drawRect(left, top, right, bottom, lapiz);
                        break;
                    case 3: canvas.drawRect(Lista.get(Nf).getIni().x, Lista.get(Nf).getIni().y, Lista.get(Nf).getFin().x, Lista.get(Nf).getFin().y, lapiz);
                        break;
                    case 4:
                        Path path = new Path();
                        float centerX = (Lista.get(Nf).getIni().x + Lista.get(Nf).getFin().x) / 2;
                        float centerY = (Lista.get(Nf).getIni().y + Lista.get(Nf).getFin().y) / 2;
                        float radius = Math.abs(Lista.get(Nf).getFin().x - Lista.get(Nf).getIni().x) / 2;

                        // Calcular las coordenadas de los vértices del pentágono
                        float angle = (float) (Math.PI * 2 / 5); // Ángulo entre los vértices
                        float rotation = (float) (Math.PI / 2); // Rotación inicial para que el primer vértice esté en la parte superior

                        for (int i = 0; i < 5; i++) {
                            float x = centerX + (float) (radius * Math.cos(angle * i + rotation));
                            float y = centerY + (float) (radius * Math.sin(angle * i + rotation));

                            if (i == 0) {
                                path.moveTo(x, y);
                            } else {
                                path.lineTo(x, y);
                            }
                        }

                        path.close(); // Cierra el pentágono, dibujando una línea desde el último vértice hasta el primero

                        canvas.drawPath(path, lapiz);
                        break;
                    case 5: float difX=Lista.get(Nf).getFin().x-Lista.get(Nf).getIni().x;
                        float difY=Lista.get(Nf).getFin().y-Lista.get(Nf).getIni().y;
                        float radio=(float)sqrt( (difX*difX) + (difY)*(difY) );
                        canvas.drawCircle(Lista.get(Nf).getIni().x,Lista.get(Nf).getIni().y,radio,lapiz);
                        break;

                    case 6: canvas.drawOval(Lista.get(Nf).getIni().x, Lista.get(Nf).getIni().y, Lista.get(Nf).getFin().x, Lista.get(Nf).getFin().y, lapiz);
                        break;
                }
            }
        }
    }//FIN DE LA CLASE MiPlano
    private void guardarArchivo() {
        String archivo = "figuras.txt";
        StringBuilder contenido = new StringBuilder();

        for (Figuras figura : Lista) {
            contenido.append(figura.getTipoFigura()).append(",");
            contenido.append(figura.getColor()).append(",");
            contenido.append(figura.getIni().x).append(",");
            contenido.append(figura.getIni().y).append(",");
            contenido.append(figura.getFin().x).append(",");
            contenido.append(figura.getFin().y).append("\n");
        }

        try {
            FileOutputStream outputStream = openFileOutput(archivo, Context.MODE_PRIVATE);
            outputStream.write(contenido.toString().getBytes());
            outputStream.close();
            Toast.makeText(this, "Archivo guardado correctamente", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error al guardar el archivo", Toast.LENGTH_SHORT).show();
        }
    }

    private void leerArchivo() {
        String archivo = "figuras.txt";
        Lista.clear();

        try {
            FileInputStream inputStream = openFileInput(archivo);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String linea;
            while ((linea = bufferedReader.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 6) {
                    Figuras figura = new Figuras();
                    figura.setTipoFigura(Integer.parseInt(datos[0]));
                    figura.setColor(Integer.parseInt(datos[1]));
                    figura.setIni(new Point(Integer.parseInt(datos[2]), Integer.parseInt(datos[3])));
                    figura.setFin(new Point(Integer.parseInt(datos[4]), Integer.parseInt(datos[5])));
                    Lista.add(figura);
                }
            }

            inputStream.close();
            Toast.makeText(this, "Archivo leído correctamente", Toast.LENGTH_SHORT).show();
            Miplano.invalidate();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error al leer el archivo", Toast.LENGTH_SHORT).show();
        }
    }
}//FIN DE LA CLASE PRINCIPAL