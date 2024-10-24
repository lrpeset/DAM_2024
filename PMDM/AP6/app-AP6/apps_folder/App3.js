import React, { useState } from "react";
import { Button, View, StyleSheet } from "react-native";

export default function App3() {
  const fetchGitHubUsers = async () => {
    try {
      const response = await fetch("https://api.github.com/search/users?q=Java");
      const data = await response.json();

      if (data.items && data.items.length > 0) {
        console.log("Primer perfil:", data.items[0]);
      } else {
        console.log("No se encontraron usuarios.");
      }
    } catch (error) {
      console.log("Error en la solicitud:", error);
    }
  };

  return (
    <View style={styles.container}>
      <Button title="Buscar usuarios de Java" onPress={fetchGitHubUsers} />
    </View>
  );
}

const styles = StyleSheet.create({
  container: { flex: 1, justifyContent: "center", alignItems: "center" },
});
