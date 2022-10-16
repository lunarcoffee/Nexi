# Nexi
A highly experimental programming language for experimentation and learning how compilers work. This will probably never be stable.

Example source:
```
s32 main: {
    s32 value = func();
    return value;
}

s32 func: {
    s32 var = 5 - 3 % 2;
    return 2 * (3 + 4) / var;
}
```

Example x86 assembly output:
```asm
global main
main:
call func
mov ebx, eax
mov eax, ebx
ret
global func
func:
mov eax, 5
push eax
mov eax, 3
push eax
mov eax, 2
mov ecx, eax             
pop eax                  
xor edx, edx
div ecx
mov eax, edx             
pop edx
sub edx, eax
mov eax, edx
mov ebx, eax
mov eax, 2
push eax
mov eax, 3
push eax
mov eax, 4
pop edx
add eax, edx
pop edx
imul eax, edx
push eax
mov eax, ebx
mov ecx, eax             
pop eax                  
xor edx, edx
div ecx
ret
```
These files can be found in `src/test/resources` as `test0.nxi` and `test0.asm`, respectively.
