import { StyleSheet, View } from "react-native";

export default function App1() {
  const filas = [["blue"], ["red"]];

  const renderCuadrado = (color, index) => {
    const customStyle = color === "red" ? styles.largeCuadrado : styles.cuadrado;

    return <View key={index} style={[customStyle, { backgroundColor: color }]} />;
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
    width: 100,
    height: 100,
  },
  largeCuadrado: {
    width: 200,
    height: 200,
  },
});
