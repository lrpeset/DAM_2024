import React, { useState } from "react";
import { View, SafeAreaView, StyleSheet, TextInput, Button, Alert, Text } from "react-native";

export default function App5() {
  const [DNIValue, setDNIValue] = useState("");
  const [nameInput, setNameInput] = useState("");
  const [entries, setEntries] = useState([]);

  const isValidDNI = (dni) => {
    const dniRegex = /^\d{8}[A-Z]$/;
    return dniRegex.test(dni);
  };

  const handleAddDNIAndName = () => {
    if (!DNIValue || !nameInput) {
      Alert.alert("Error", "Por favor, inserta tanto el DNI como el nombre.");
      return;
    }

    if (!isValidDNI(DNIValue)) {
      Alert.alert("Error", "El DNI debe tener 8 números y 1 letra mayúscula al final.");
      return;
    }

    const newEntry = { dni: DNIValue, name: nameInput };
    setEntries([...entries, newEntry]);
    setDNIValue("");
    setNameInput("");
  };

  return (
    <SafeAreaView style={styles.container}>
      <View style={styles.innerContainer}>
        <Text style={styles.titleText}>DNI</Text>
        <TextInput
          style={styles.input}
          placeholder="Inserta tu DNI..."
          value={DNIValue}
          onChangeText={setDNIValue}
        />

        <Text style={styles.titleText}>Nombre</Text>
        <TextInput
          style={styles.input}
          placeholder="Inserta tu nombre..."
          value={nameInput}
          onChangeText={setNameInput}
        />
        <Button title="PULSA..." onPress={handleAddDNIAndName} />

        <Text>Nombre / DNI</Text>
        {entries.map((entry, index) => (
          <Text key={index} style={styles.outputText}>
            {entry.name} / {entry.dni}
          </Text>
        ))}
      </View>
    </SafeAreaView>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
  },
  innerContainer: {
    width: "80%",
    alignItems: "center",
  },
  input: {
    height: 40,
    margin: 12,
    padding: 10,
    borderWidth: 1,
    width: "100%",
  },
  titleText: {
    fontSize: 30,
    marginVertical: 5,
  },
  outputText: {
    fontSize: 20,
    marginVertical: 5,
  },
});
