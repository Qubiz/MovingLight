package qubiz.movinglight;

import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PaintDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SeekBar;


public class MainActivity extends ActionBarActivity {

    private SeekBar seekBarHue;
    private SeekBar seekBarSaturation;
    private SeekBar seekBarBrightness;

    float hue;
    float saturation;
    float brightness;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //INITIALIZATION
        initialize();

        detectChanges(seekBarHue);
        detectChanges(seekBarSaturation);
        detectChanges(seekBarBrightness);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private Drawable createHUEDrawable() {
        final int[] hue_color_range = {
                Color.HSVToColor(new float[] {0, saturation, brightness}),
                Color.HSVToColor(new float[] {60, saturation, brightness}),
                Color.HSVToColor(new float[] {120, saturation, brightness}),
                Color.HSVToColor(new float[] {180, saturation, brightness}),
                Color.HSVToColor(new float[] {240, saturation, brightness}),
                Color.HSVToColor(new float[] {300, saturation, brightness}),
                Color.HSVToColor(new float[] {360, saturation, brightness})
        };

        final float[] positions = {0,0.16f,0.33f,0.5f,0.66f, 0.83f, 1};

        ShapeDrawable.ShaderFactory shaderFactory = new ShapeDrawable.ShaderFactory() {
            @Override
            public Shader resize(int width, int height) {
                return new LinearGradient(0, 0,
                        seekBarHue.getWidth(),
                        0,
                        hue_color_range,
                        positions,
                        Shader.TileMode.REPEAT);
            }
        };
        PaintDrawable paintDrawable = new PaintDrawable();
        paintDrawable.setShape(new RectShape());
        paintDrawable.setShaderFactory(shaderFactory);
        return paintDrawable;
    }

    private Drawable createSaturationDrawable() {
        final int[] colors = {
            Color.HSVToColor(new float[] {hue, 0, brightness}),
            Color.HSVToColor(new float[] {hue, 1, brightness})
        };

        final float[] positions = {0, 1};

        ShapeDrawable.ShaderFactory shaderFactory = new ShapeDrawable.ShaderFactory() {
            @Override
            public Shader resize(int width, int height) {
                return new LinearGradient(0, 0,
                        seekBarSaturation.getWidth(),
                        0,
                        colors,
                        positions,
                        Shader.TileMode.REPEAT);
            }
        };
        PaintDrawable paintDrawable = new PaintDrawable();
        paintDrawable.setShape(new RectShape());
        paintDrawable.setShaderFactory(shaderFactory);
        return paintDrawable;
    }

    private Drawable createBrightnessDrawable() {
        final int[] colors = {
                Color.HSVToColor(new float[] {hue, saturation, 0}),
                Color.HSVToColor(new float[] {hue, saturation, 1})
        };

        final float[] positions = {0, 1};

        ShapeDrawable.ShaderFactory shaderFactory = new ShapeDrawable.ShaderFactory() {
            @Override
            public Shader resize(int width, int height) {
                return new LinearGradient(0, 0,
                        seekBarBrightness.getWidth(),
                        0,
                        colors,
                        positions,
                        Shader.TileMode.REPEAT);
            }
        };
        PaintDrawable paintDrawable = new PaintDrawable();
        paintDrawable.setShape(new RectShape());
        paintDrawable.setShaderFactory(shaderFactory);
        return paintDrawable;
    }

    private void detectChanges(SeekBar seekBar) {
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                switch(seekBar.getId()) {
                    case R.id.seek_bar_hue:
                        hue = progress;
                        seekBarSaturation.setProgressDrawable(createSaturationDrawable());
                        seekBarBrightness.setProgressDrawable(createBrightnessDrawable());
                        break;
                    case R.id.seek_bar_saturation:
                        saturation = progress/100f;
                        seekBarHue.setProgressDrawable(createHUEDrawable());
                        seekBarBrightness.setProgressDrawable(createBrightnessDrawable());
                        break;
                    case R.id.seek_bar_brightness:
                        brightness = progress/100f;
                        seekBarSaturation.setProgressDrawable(createSaturationDrawable());
                        seekBarHue.setProgressDrawable(createHUEDrawable());
                        break;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void initialize() {
        seekBarHue          = (SeekBar) findViewById(R.id.seek_bar_hue);
        seekBarSaturation   = (SeekBar) findViewById(R.id.seek_bar_saturation);
        seekBarBrightness   = (SeekBar) findViewById(R.id.seek_bar_brightness);

        hue         = seekBarHue.getProgress();
        saturation  = seekBarSaturation.getProgress()/100f;
        brightness  = seekBarBrightness.getProgress()/100f;


        seekBarHue.setProgressDrawable(createHUEDrawable());
        seekBarSaturation.setProgressDrawable(createSaturationDrawable());
        seekBarBrightness.setProgressDrawable(createBrightnessDrawable());
    }
}
