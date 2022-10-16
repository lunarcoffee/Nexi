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