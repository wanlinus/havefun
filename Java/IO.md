# Java IO

[TOC]

## 字节流

基础IO

```java
// 读取并显示文件
private void byteStream() throws IOException {
    try (FileInputStream fis = new FileInputStream("infile.txt");
         FileOutputStream fos = new FileOutputStream("outfile.txt")) {
        final int available = fis.available();
        byte[] read = new byte[available];
        fis.read(read);
        String str = new String(read, StandardCharsets.UTF_8);
        System.out.println(str);
        fos.write(str.getBytes(StandardCharsets.UTF_8));
    }
}
// 文件复制
private void copyFile(String src, String dst) throws IOException {
    try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(src));
         BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(dst))) {
        byte[] bytes = new byte[64];
        int i;
        while ((i = bis.read(bytes)) != -1) {
            bos.write(bytes, 0, i);
        }
        bos.flush();
    }
}
```

## 字符流

就是对字节流的包装, 适用于对字符的IO

```java
private static void charReaderWriter() throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader("infile.txt", StandardCharsets.UTF_8));
         BufferedWriter writer = new BufferedWriter(new FileWriter("writer.txt", StandardCharsets.UTF_8))) {
        reader.lines().forEach(s -> {
            try {
                writer.write(s);
                writer.newLine();
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
```

