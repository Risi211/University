// basic file operations
#include <iostream>
#include <fstream>
using namespace std;

int main () {
  ofstream myfile;
  myfile.open ("example.txt");
  myfile << "Writing this to a file.\n";
  myfile.close();

  ofstream f2 ("example2.txt");
  if (f2.is_open())
  {
    f2 << "This is a line.\n";
    f2 << "This is another line.\n";
    f2.close();
  }
  else cout << "Unable to open file";

  //size binary file
  streampos begin,end;
  ifstream myfile2 ("example.txt", ios::binary);
  begin = myfile2.tellg();
  myfile2.seekg (0, ios::end);
  end = myfile2.tellg();
  myfile2.close();
  cout << "size is: " << (end-begin) << " bytes.\n";

  //load binary file
  streampos size;
  char * memblock;
  ifstream file ("example.txt", ios::in|ios::binary|ios::ate);
  if (file.is_open())
  {
    size = file.tellg();
    memblock = new char [size];
    file.seekg (0, ios::beg);
    file.read (memblock, size);
    file.close();

    cout << "the entire file content is in memory";

    delete[] memblock;
  }
  else cout << "Unable to open file";

  return 0;
}


