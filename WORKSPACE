workspace(name = "org_mizux_bazelswig")
load("@bazel_tools//tools/build_defs/repo:git.bzl", "git_repository", "new_git_repository")

# Bazel Extensions
## Bazel Skylib rules.
git_repository(
    name = "bazel_skylib",
    tag = "1.4.2",
    remote = "https://github.com/bazelbuild/bazel-skylib.git",
)
load("@bazel_skylib//:workspace.bzl", "bazel_skylib_workspace")
bazel_skylib_workspace()

## Bazel rules.
git_repository(
    name = "platforms",
    tag = "0.0.7",
    remote = "https://github.com/bazelbuild/platforms.git",
)

git_repository(
    name = "rules_cc",
    tag = "0.0.8",
    remote = "https://github.com/bazelbuild/rules_cc.git",
)

git_repository(
    name = "rules_java",
    tag = "6.4.0",
    #tag = "6.5.1",
    remote = "https://github.com/bazelbuild/rules_java.git",
)

git_repository(
    name = "rules_jvm_external",
    tag = "5.2",
    #tag = "5.3",
    remote = "https://github.com/bazelbuild/rules_jvm_external.git",
)

git_repository(
    name = "contrib_rules_jvm",
    tag = "v0.9.0",
    #tag = "v0.18.0",
    remote = "https://github.com/bazel-contrib/rules_jvm.git",
)

git_repository(
    name = "rules_python",
    tag = "0.24.0",
    remote = "https://github.com/bazelbuild/rules_python.git",
)

# Dependencies
## Swig support
# pcre source code repository
new_git_repository(
    name = "pcre2",
    build_file = "//bazel:pcre2.BUILD",
    tag = "pcre2-10.42",
    remote = "https://github.com/PCRE2Project/pcre2.git",
)

# generate Patch:
#   Checkout swig
#   cd Source/CParse && bison -d -o parser.c parser.y
#   ./autogen.sh
#   ./configure
#   make Lib/swigwarn.swg
#   edit .gitignore and remove parser.h, parser.c, and swigwarn.swg
#   git add Source/CParse/parser.h Source/CParse/parser.c Lib/swigwarn.swg
#   git diff --staged Lib Source/CParse > <path to>swig.patch
# Edit swig.BUILD:
#   edit version
new_git_repository(
    name = "swig",
    build_file = "//bazel:swig.BUILD",
    patches = ["//bazel:swig.patch"],
    patch_args = ["-p1"],
    tag = "v4.1.1",
    remote = "https://github.com/swig/swig.git",
)

# Python
load("@rules_python//python:repositories.bzl", "py_repositories")
py_repositories()

# Create a central external repo, @pip_deps, that contains Bazel targets for all the
# third-party packages specified in the requirements.txt file.
load("@rules_python//python:pip.bzl", "pip_parse")
pip_parse(
   name = "pip_deps",
   requirements = "//:requirements.txt",
)

load("@pip_deps//:requirements.bzl", install_pip_deps="install_deps")
install_pip_deps()

## Java support (with junit 5)
load("@rules_java//java:repositories.bzl", "rules_java_dependencies", "rules_java_toolchains")
rules_java_dependencies()
rules_java_toolchains()

load("@rules_jvm_external//:repositories.bzl", "rules_jvm_external_deps")
rules_jvm_external_deps()

load("@rules_jvm_external//:setup.bzl", "rules_jvm_external_setup")
rules_jvm_external_setup()

JUNIT_PLATFORM_VERSION = "1.9.2"
JUNIT_JUPITER_VERSION = "5.9.2"
load("@rules_jvm_external//:defs.bzl", "maven_install")
maven_install(
    artifacts = [
        "net.java.dev.jna:jna:aar:5.13.0",
        "com.google.truth:truth:0.32",
        "org.junit.platform:junit-platform-launcher:%s" % JUNIT_PLATFORM_VERSION,
        "org.junit.platform:junit-platform-reporting:%s" % JUNIT_PLATFORM_VERSION,
        "org.junit.jupiter:junit-jupiter-api:%s" % JUNIT_JUPITER_VERSION,
        "org.junit.jupiter:junit-jupiter-params:%s" % JUNIT_JUPITER_VERSION,
        "org.junit.jupiter:junit-jupiter-engine:%s" % JUNIT_JUPITER_VERSION,
    ],
    repositories = [
        "https://repo1.maven.org/maven2",
    ],
)

load("@contrib_rules_jvm//:repositories.bzl", "contrib_rules_jvm_deps")
contrib_rules_jvm_deps()

load("@contrib_rules_jvm//:setup.bzl", "contrib_rules_jvm_setup")
contrib_rules_jvm_setup()

## Testing
git_repository(
    name = "com_google_googletest",
    tag = "v1.13.0",
    remote = "https://github.com/google/googletest.git",
)

git_repository(
    name = "com_google_benchmark",
    tag = "v1.8.1",
    remote = "https://github.com/google/benchmark.git",
)
