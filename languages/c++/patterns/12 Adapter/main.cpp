#include <iostream>
#include <string>
#include <vector>

using namespace std;

class MediaPlayer{ //abstract
  public:
    virtual void play(string path, string format) = 0;
};

class AdvancedMediaPlayer{ //abstract
  public:
    virtual void playVlc(string path) = 0;
    virtual void playMp4(string path) = 0;
};

class VlcPlayer : public AdvancedMediaPlayer{
  public:
    void playVlc(string path){
	cout << "reproducing vlc: " << path << "\r\n";	
    }
    void playMp4(string path){
	cout << "ERROR: vlc cannot reproduce mp4: " << path << "\r\n";		
    }
};

class Mp4Player : public AdvancedMediaPlayer{
  public:
    void playMp4(string path){
	cout << "reproducing mp4: " << path << "\r\n";	
    }
    void playVlc(string path){
	cout << "ERROR: mp4 cannot reproduce vlc: " << path << "\r\n";		
    }
};

class MediaAdapter : public MediaPlayer{
  private:
    AdvancedMediaPlayer* amp;
  public:
    void play(string path, string format){
	if(format == "vlc"){
		amp = new VlcPlayer;
		amp->playVlc(path);
		delete amp;
	}
	if(format == "mp4"){
		amp = new Mp4Player;
		amp->playMp4(path);
		delete amp;
	}
    }
};

class SimplePlayer : public MediaPlayer{
  public:
    void play(string path, string format){
	if(format == "mp3"){ //native player
		cout << "mp3 native player: " << path << "\r\n";
	}
	else{
		MediaAdapter ma;
		ma.play(path, format);		
	}
    }
};

int main()
{
  SimplePlayer sp;
  sp.play("p1","mp3");
  sp.play("p2","vlc");
  sp.play("p3","mp4");
  return 0;
}
