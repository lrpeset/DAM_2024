import React, { useState } from "react";
import {
  View,
  TextInput,
  Text,
  TouchableOpacity,
  StyleSheet,
  Animated,
  Keyboard,
} from "react-native";
import Icon from "react-native-vector-icons/MaterialCommunityIcons";

const CalculadoraIMC = () => {
  const [peso, setPeso] = useState("");
  const [altura, setAltura] = useState("");
  const [resultado, setResultado] = useState(null);
  const [mensaje, setMensaje] = useState("");
  const [color, setColor] = useState("#000");
  const [fadeAnim] = useState(new Animated.Value(0));

  const calcularIMC = () => {
    Keyboard.dismiss();

    const pesoNum = parseFloat(peso);
    const alturaNum = parseFloat(altura) / 100;
    if (!pesoNum || !alturaNum || alturaNum <= 0) {
      setMensaje("Por favor ingrese valores válidos.");
      setColor("#000");
      setResultado(null);
      return;
    }

    const imc = pesoNum / alturaNum ** 2;
    setResultado(imc.toFixed(2));

    if (imc < 18.5) {
      setMensaje("Peso insuficiente");
      setColor("#4CAF50");
    } else if (imc < 25) {
      setMensaje("Normopeso");
      setColor("#4CAF50");
    } else if (imc < 27) {
      setMensaje("Sobrepeso grado I");
      setColor("#4CAF50");
    } else if (imc < 30) {
      setMensaje("Sobrepeso grado II (preobesidad)");
      setColor("#FFC107");
    } else if (imc < 35) {
      setMensaje("Obesidad de tipo I");
      setColor("#FFC107");
    } else if (imc < 40) {
      setMensaje("Obesidad de tipo II");
      setColor("#FFC107");
    } else if (imc < 50) {
      setMensaje("Obesidad de tipo III (mórbida)");
      setColor("#F44336");
    } else {
      setMensaje("Obesidad de tipo IV (extrema)");
      setColor("#F44336");
    }

    Animated.timing(fadeAnim, {
      toValue: 1,
      duration: 1000,
      useNativeDriver: true,
    }).start(() => {
      Animated.timing(fadeAnim, {
        toValue: 0,
        duration: 1000,
        delay: 4000,
        useNativeDriver: true,
      }).start();
    });
  };

  return (
    <View style={styles.container}>
      <Text style={styles.title}>Calculadora de IMC</Text>
      <View style={styles.inputContainer}>
        <Icon name="weight-kilogram" size={24} color="#3E4A89" style={styles.icon} />
        <TextInput
          style={styles.input}
          placeholder="Peso (kg)"
          keyboardType="numeric"
          value={peso}
          onChangeText={setPeso}
        />
      </View>
      <View style={styles.inputContainer}>
        <Icon name="human-male-height" size={24} color="#3E4A89" style={styles.icon} />
        <TextInput
          style={styles.input}
          placeholder="Altura (cm)"
          keyboardType="numeric"
          value={altura}
          onChangeText={setAltura}
        />
      </View>
      <TouchableOpacity style={styles.button} onPress={calcularIMC}>
        <Text style={styles.buttonText}>Calcular IMC</Text>
      </TouchableOpacity>
      {resultado && (
        <Animated.View style={[styles.resultContainer, { opacity: fadeAnim }]}>
          <Text style={styles.resultText}>IMC: {resultado}</Text>
          <Text style={[styles.messageText, { color }]}>{mensaje}</Text>
        </Animated.View>
      )}
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    alignItems: "center",
    padding: 20,
    backgroundColor: "#fff",
    borderRadius: 10,
    elevation: 5,
    shadowColor: "#000",
    shadowOffset: { width: 0, height: 4 },
    shadowOpacity: 0.2,
    shadowRadius: 5,
    width: "90%",
  },
  title: {
    fontSize: 24,
    fontWeight: "bold",
    color: "#3E4A89",
    marginBottom: 20,
  },
  inputContainer: {
    flexDirection: "row",
    alignItems: "center",
    width: "100%",
    marginVertical: 10,
  },
  icon: {
    marginHorizontal: 10,
  },
  input: {
    flex: 1,
    height: 50,
    borderColor: "#ddd",
    borderWidth: 1,
    borderRadius: 10,
    paddingHorizontal: 15,
    backgroundColor: "#f9f9f9",
    fontSize: 16,
  },
  button: {
    backgroundColor: "#3E4A89",
    paddingVertical: 15,
    paddingHorizontal: 30,
    borderRadius: 10,
    width: "100%",
    alignItems: "center",
    marginTop: 20,
  },
  buttonText: {
    color: "#fff",
    fontSize: 18,
    fontWeight: "bold",
  },
  resultContainer: {
    marginTop: 20,
    padding: 15,
    borderRadius: 10,
    backgroundColor: "#f5f5f5",
    width: "100%",
    alignItems: "center",
    borderWidth: 1,
    borderColor: "#ddd",
  },
  resultText: {
    fontSize: 18,
    fontWeight: "bold",
    color: "#333",
  },
  messageText: {
    fontSize: 16,
    marginTop: 10,
    textAlign: "center",
  },
});

export default CalculadoraIMC;
