global main
main:
call func
ret
global func
func:
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
mov eax, 5
push eax
mov eax, 1
pop edx
sub edx, eax
mov eax, edx
mov ecx, eax             
pop eax                  
xor edx, edx             
idiv ecx
ret