StreamSim: adjustable stream simulation interface

StreamSim is a project which aims at generating, recording and replaying streams for simulations. 

--> Creating a stream schema:
Each stream is defined with a schema. It lists attributes describing each item of the stream. 
Each attribute is defined with an unique name, a type and options relative to its type. For the moment, StreamSim supports integer, text and enumerate attribute types. 
Integer attributes are defined thanks to a min-max interval of values.
Textual attributes are defined with a pattern and a length so, for each item, a random string of characters is generated following the pattern and the specified length.
Enumerate attributes represent attributes which take only specific values. They handle strings like numbers but each value must but declared explicitly.

--> Generating a stream:
After the definition of the schema, users can generate a set of items respecting a chosen schema. This stream is defined by a port of emission and a variation model.
StreamSim supports the following variations with uniform distributions: linear, exponential, logarithmic increase and decrease of the output rate.
In addition, it is also possible to generate a stream with a constant output rate or a combination of all models. 
Zipf 0.5, 1, 1.5 and 2 distributions are also supported with constant output rates. Each variation model presented above generates a 10min-stream.
After that generation, users can perform three actions with the stream: play it on the specified port, record it in a database or replay the stream recorded previously with same name and variation.

--> Playing a stream:
There are two ways to play a stream: apply a variation model and select "play the stream" at the generation step or select the checkbox "Control stream rate with the live board".
The first way plays a 10-min stream which can be paused and restarted at any time. Note that a stream cannot be restarted after its termination and must be regenerated.
The second way plays a stream with an adjustable output rate at runtime. Without user intervention, StreamSim plays a 10-min stream with a constant ouput rate. The value remains the last one set by the user.

