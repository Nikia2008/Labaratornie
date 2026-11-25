import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.regex.*;


static Set<String> keep = Set.of(
        "abstract", "assert", "boolean", "break", "byte", "case", "catch", "char", "class",
        "const", "continue", "default", "do", "double", "else", "enum", "extends", "final",
        "finally", "float", "for", "goto", "if", "implements", "import", "instanceof", "int",
        "interface", "long", "native", "new", "package", "private", "protected", "public",
        "return", "short", "static", "strictfp", "super", "switch", "synchronized", "this",
        "throw", "throws", "transient", "try", "void", "volatile", "while", "true", "false",
        "null", "var", "Main", "String", "System"
);

static Map<String, String> map = new HashMap<>();
static int n = 0;

void main(String[] args) throws IOException {
    Path src = Path.of(args[0]);
    Path dst = Path.of(args[1]);

    for (Path p : (Iterable<Path>) Files.walk(src).filter(f -> f.toString().endsWith(".java"))::iterator) {
        String code = Files.readString(p);
        code = code.replaceAll("//[^\n]*", "");
        code = code.replaceAll("(?s)/\\*.*?\\*/", "");

        Matcher m = Pattern.compile("\\b[A-Za-z_$][A-Za-z0-9_$]*\\b").matcher(code);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            String w = m.group();
            String r = keep.contains(w) ? w : map.computeIfAbsent(w, k -> "v" + (n++));
            m.appendReplacement(sb, r);
        }
        m.appendTail(sb);

        String result = sb.toString().replaceAll("\\s+", " ").trim();
        Path out = dst.resolve(src.relativize(p));
        Files.createDirectories(out.getParent());
        Files.writeString(out, result);
    }
}
