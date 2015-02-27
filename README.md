***ANDROID WEAR REMOTE FOR MIAMI***

Sample application that uses BboxAPI to control Bbox Miami with a Smartwatch (Android Wear).

Project uses Anymote library (Google) to zap and navigate in the UI.
Project also uses BboxAPI 0.3.1 library to retrieve the live program guide.

'mobile' module concerns Smartphone part:<br/>
   - Wi-Fi connection to Bbox Miami<br/>
   - request management to the API<br/>
   - communication with smartwatch

'wear' module concerns Smartwatch:<br/>
   - display live program guide results (title, channel, progression)<br/>
   - sending commands to zap or navigate to Smartphone