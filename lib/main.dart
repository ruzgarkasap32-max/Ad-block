import 'package:flutter/material.dart';

void main() {
  runApp(const AdBlockApp());
}

class AdBlockApp extends StatelessWidget {
  const AdBlockApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      title: 'AD BLOCK',
      theme: ThemeData.dark(),
      home: const AnaSayfa(),
    );
  }
}

class AnaSayfa extends StatefulWidget {
  const AnaSayfa({super.key});

  @override
  State<AnaSayfa> createState() => _AnaSayfaState();
}

class _AnaSayfaState extends State<AnaSayfa> {
  bool koruma = false;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: const Color(0xFF081426),
      appBar: AppBar(
        title: const Text('AD BLOCK'),
        centerTitle: true,
        backgroundColor: const Color(0xFF102B49),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            const Icon(
              Icons.shield,
              color: Colors.blue,
              size: 110,
            ),
            const SizedBox(height: 25),
            Text(
              koruma ? 'KORUMA AÇIK' : 'KORUMA KAPALI',
              style: const TextStyle(
                fontSize: 25,
                fontWeight: FontWeight.bold,
              ),
            ),
            const SizedBox(height: 30),
            ElevatedButton(
              onPressed: () {
                setState(() {
                  koruma = !koruma;
                });
              },
              child: Text(
                koruma ? 'KORUMAYI DURDUR' : 'KORUMAYI BAŞLAT',
              ),
            ),
            const SizedBox(height: 25),
            const Text(
              'AD BLOCK BY RÜZGAR',
              style: TextStyle(color: Colors.white54),
            ),
          ],
        ),
      ),
    );
  }
}
