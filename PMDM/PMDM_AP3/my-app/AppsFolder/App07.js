import { useState } from "react";
import { Pressable, View, StyleSheet, TextInput, Text, Alert } from "react-native";

export default function App07() {
  const [input, setInput] = useState("");

  const handlePress = () => {
    const regex = /^\d{8}[A-Z]$/;

    if (regex.test(input)) {
      Alert.alert("DNI introducido válido");
    } else if (input === "") {
      Alert.alert("No se ha introducido nada");
    } else {
      Alert.alert("El DNI debe tener 8 números y tiene que acabar en letra");
    }
  };

  return (
    <View style={styles.container}>
      <Text style={styles.title}>Validador DNI</Text>
      <TextInput style={styles.input} placeholder="Inserta DNI" onChangeText={(text) => setInput(text)} value={input} />
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
