import { useState } from "react";
import { Pressable, View, StyleSheet, TextInput, Text, Alert } from "react-native";

export default function App05() {
  const [input, setInput] = useState("");
  const [result, setResult] = useState("");

  const handlePress = () => {
    if (input === "") {
      Alert.alert("No se ha introducido nada");
    } else if (!isNaN(input)) {
      const kmToMiles = (parseFloat(input) * 0.62).toFixed(2);
      setResult(kmToMiles);
    } else {
      Alert.alert("Introduce un número válido");
    }
  };

  return (
    <View style={styles.container}>
      <Text style={styles.title}>Convertidor de km a millas</Text>
      <TextInput style={styles.input} placeholder="Inserta kilómetros" onChangeText={(text) => setInput(text)} value={input} />
      <Text style={styles.title}>{result ? `${result} millas` : ""}</Text>
      <Pressable style={styles.button} onPress={handlePress}>
        <Text style={styles.text}>Pulsa...</Text>
      </Pressable>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
    backgroundColor: "white",
  },
  title: {
    fontSize: 24,
    marginBottom: 20,
  },
  input: {
    marginBottom: 20,
    width: 200,
    textAlign: "center",
  },
  button: {
    backgroundColor: "blue",
    padding: 10,
  },
  text: {
    fontSize: 16,
    color: "white",
  },
});
