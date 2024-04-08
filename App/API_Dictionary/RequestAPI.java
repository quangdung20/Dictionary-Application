package API_Dictionary;

import com.voicerss.tts.*;
import javafx.concurrent.Task;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.io.FileOutputStream;

import static com.voicerss.tts.Languages.*;
//import static com.voicerss.tts.Voices.*;
//import static com.voicerss.tts.Voices.English_UnitedStates.*;


public class RequestAPI extends Task<Clip> {
    private String text;
    public static double speed = 1;
    public static String language = "English_UnitedStates";
    public static String Name = "Linda";
    public RequestAPI(String text) {
        this.text = text;
    }

    @Override
    protected Clip call() throws Exception {
        String PATH = "App/resources/Data/audio.wav";
        VoiceProvider tts = new VoiceProvider("11e9655235c943cebe3df1a1a7abdb14");
        VoiceParameters params = new VoiceParameters(text, English_UnitedStates);
        params.setCodec(AudioCodec.WAV);
        params.setFormat(AudioFormat.Format_44KHZ.AF_44khz_16bit_stereo);
        params.setBase64(false);
        params.setLanguage(English_UnitedStates);
        params.setVoice(Voices.English_UnitedStates.Linda);
        params.setSSML(false);
        params.setRate((int) Math.round(-2.9936 * speed * speed + 15.2942 * speed - 12.7612));

        byte[] voice;
        voice = tts.speech(params);
        FileOutputStream fos = new FileOutputStream(PATH);
        fos.write(voice, 0, voice.length);
        fos.flush();
        fos.close();
        File audioFile = new File(PATH);
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
        Clip clip = AudioSystem.getClip();
        clip.open(audioStream);
        return clip;
    }
}
