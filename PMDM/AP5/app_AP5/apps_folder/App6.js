import React, { useState } from "react";
import { View, Text, Image, StyleSheet } from "react-native";

export default function App6() {
  const [articles, setArticles] = useState([
    {
      id: 1,
      title: "Título 1",
      imageUrl: "https://via.placeholder.com/150",
      body: "Este es el contenido del artículo 1",
    },
    {
      id: 2,
      title: "Título 2",
      imageUrl: "https://via.placeholder.com/150",
      body: "Este es el contenido del artículo 2",
    },
    {
      id: 3,
      title: "Título 3",
      imageUrl: "https://via.placeholder.com/150",
      body: "Este es el contenido del artículo 3",
    },
  ]);

  const handleTitlePress = (id) => {
    setArticles((prevArticles) =>
      prevArticles.map((article) =>
        article.id === id
          ? {
              ...article,
              title: `Nuevo título para el Artículo ${id}`,
              imageUrl: `https://via.placeholder.com/150?text=Imagen+${id}`,
              body: `Este es el nuevo contenido del Artículo ${id}.`,
            }
          : article
      )
    );
  };

  return (
    <View style={styles.container}>
      {articles.map((article) => (
        <Article
          key={article.id}
          title={article.title}
          imageUrl={article.imageUrl}
          body={article.body}
          onTitlePress={() => handleTitlePress(article.id)}
        />
      ))}
    </View>
  );
}

function Article({ title, imageUrl, body, onTitlePress }) {
  return (
    <View style={styles.articleContainer}>
      <Text style={styles.titleText} onPress={onTitlePress}>
        {title}
      </Text>
      <Image style={styles.image} source={{ uri: imageUrl }} />
      <Text style={styles.bodyText}>{body}</Text>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
    padding: 20,
  },
  articleContainer: {
    marginBottom: 20,
    alignItems: "center",
    padding: 15,
    width: "100%",
  },
  titleText: {
    fontSize: 20,
    fontWeight: "bold",
    textDecorationLine: "underline",
  },
  image: {
    width: 150,
    height: 150,
  },
  bodyText: {
    fontSize: 12,
    textAlign: "center",
  },
});
