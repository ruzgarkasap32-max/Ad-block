class AdBlockFilterEngine {
  final Set<String> blockedDomains = {};
  final Set<String> allowedDomains = {};

  void loadRules(String text) {
    for (final raw in text.split('\n')) {
      final line = raw.trim();

      if (line.isEmpty || line.startsWith('!')) continue;

      if (line.startsWith('@@||') && line.endsWith('^')) {
        allowedDomains.add(line.substring(4, line.length - 1));
        continue;
      }

      if (line.startsWith('||') && line.endsWith('^')) {
        blockedDomains.add(line.substring(2, line.length - 1));
        continue;
      }

      final parts = line.split(RegExp(r'\s+'));

      if (parts.length >= 2 &&
          (parts[0] == '0.0.0.0' || parts[0] == '127.0.0.1')) {
        blockedDomains.addAll(parts.skip(1));
      }
    }
  }

  bool shouldBlock(String host) {
    final domain = host.toLowerCase().trim();

    for (final allowed in allowedDomains) {
      if (domain == allowed || domain.endsWith('.$allowed')) {
        return false;
      }
    }

    for (final blocked in blockedDomains) {
      if (domain == blocked || domain.endsWith('.$blocked')) {
        return true;
      }
    }

    return false;
  }
}
