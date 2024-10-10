import { StyleSheet, View } from "react-native";

export default function App2() {
  const filas = [
    ["blue", "blue"],
    ["red", "blue"],
    ["red", "red"],
  ];

  const renderCuadrado = (color, index) => {
    return <View key={index} style={[styles.cuadrado, { backgroundColor: color }]} />;
  };

  return (
    <View style={styles.container}>
      {filas.map((fila, rowIndex) => (
        <View key={rowIndex} style={styles.row}>
          {fila.map((color, squareIndex) => renderCuadrado(color, squareIndex))}
        </View>
      ))}
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: "center",
    justifyContent: "center",
  },
  row: {
    flexDirection: "row",
  },
  cuadrado: {
    width: 150,
    height: 150,
  },
});
