<h1>AWS</h1>
<hr>
<h6>Advanced Weather Station</h6>

<h3>Adapter</h3>
<ul>
    <li>Python 3.11</li>
    <li>Is a producer on RabbitMQ</li>
    <li>Serial communication with Arduino</li>
</ul>
<h3>Collector</h3>
<ul>
    <li>Java</li>
    <li>Is a consumer on RabitMQ</li>
</ul>
<h3>Arduino</h3>
<ul>
    <li>Use Humidity and temperature sensor</li>
    <li>Connect the + pin to 5V</li>
    <li>Connect the - pin to GND</li>
    <li>Connect the out pin to the digital input 2</li>
    <li>Send message of this structure: &lt;name&gt;:&lt;value&gt;:&lt;unit&gt;\n</li>
    <li>Remember to install the libraries in Arduino IDE</li>
</ul>

<h3>RabbitMQ and MongoDB</h3>
<div>
    Execute the following step:
    <ul>
        <li>Install docker</li>
        <li>Open the file docker-compose.yaml in IntelliJ</li>
        <li>Click the double green arrow next to the row numbers</li>
    </ul>   
</div>

